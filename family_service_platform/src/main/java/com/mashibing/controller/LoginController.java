package com.mashibing.controller;

import com.alibaba.fastjson.JSONObject;
import com.mashibing.bean.TblUserRecord;
import com.mashibing.returnjson.Permission;
import com.mashibing.returnjson.Permissions;
import com.mashibing.returnjson.ReturnObject;
import com.mashibing.returnjson.UserInfo;
import com.mashibing.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;
    @RequestMapping("/auth/2step-code")
    public Boolean test(){
        System.out.println("前端框架自带的验证规则。写不写无所谓");
        return true;
    }
    @RequestMapping("/auth/login")
    public  String login(@RequestParam("password") String password, @RequestParam("username") String username, HttpSession session){
        TblUserRecord tblUserRecord = loginService.login(username,password);
        tblUserRecord.setToken(tblUserRecord.getUserName());
        //将用户数据存到Session 中
        session.setAttribute("userRecord",tblUserRecord);
        System.out.println(session.getId());
        ReturnObject returnObject = new ReturnObject(tblUserRecord);
        String s = JSONObject.toJSONString(returnObject);
        return s;
    }
    @RequestMapping("/users/info")
    public String getInfo(HttpSession session){
        System.out.println(session.getId());
        TblUserRecord tblUserRecord = (TblUserRecord) session.getAttribute("userRecord");
        //获取模块信息
        String[] split = tblUserRecord.getTblRole().getRolePrivileges().split("-");
        Permissions permissions = new Permissions();
        List<Permission> permissionList = new ArrayList<>();
        for (String s : split) {
            permissionList.add(new Permission(s));
        }
        permissions.setPermissions(permissionList);
        UserInfo userInfo = new UserInfo(tblUserRecord.getUserName(),permissions);
        return JSONObject.toJSONString(new ReturnObject(userInfo));
    };
    @RequestMapping("/auth/logout")
    public String logOut(HttpSession session){
        System.out.println("logout");
        session.invalidate();;
        return "";
    };
}
