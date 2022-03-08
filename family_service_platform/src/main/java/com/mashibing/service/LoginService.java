package com.mashibing.service;

import com.mashibing.bean.TblUserRecord;
import com.mashibing.mapper.TblUserRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private TblUserRecordMapper tblUserRecordMapper;
    public TblUserRecord login(String username, String password){
        System.out.println("LoginService:"+username);
        System.out.println("LoginService:"+password);
        TblUserRecord login = tblUserRecordMapper.login(username, password);
        return login;
    }
}
