package com.mashibing.controller;

import com.alibaba.fastjson.JSONObject;
import com.mashibing.bean.FcBuilding;
import com.mashibing.bean.FcEstate;
import com.mashibing.bean.FcUnit;
import com.mashibing.bean.TblCompany;
import com.mashibing.returnjson.ReturnObject;
import com.mashibing.service.EstateService;
import com.mashibing.valueobject.UnitMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EstateController {
    @Autowired
    private EstateService estateService;
    @RequestMapping("/estate/selectCompany")
    public String selectCompany(){
        List<TblCompany> companies = estateService.selectCompany();
        return JSONObject.toJSONString(new ReturnObject(companies));
    }
    @RequestMapping("/estate/insertEstate")
    public String insertEstate(FcEstate fcEstate){
        System.out.println(fcEstate);
        Integer cnt = estateService.insertEstate(fcEstate);
        System.out.println("Cnt:"+cnt);
        if(cnt == 0){
            return JSONObject.toJSONString(new ReturnObject("0","房产编码已经存在"));
        }else{
            return JSONObject.toJSONString(new ReturnObject("1","插入房产成功"));
        }
    };

    /**
     * 此处完成的是楼宇的插入功能
     * @param buildingNumber
     * @param estateCode
     * @return
     */
    @RequestMapping("/estate/selectBuilding")
    public String selectBuilding(Integer buildingNumber,String estateCode){
        List<FcBuilding> fcBuildings = estateService.selectBuilding(buildingNumber, estateCode);
        System.out.println("fcBuildings:"+fcBuildings);
        return JSONObject.toJSONString(new ReturnObject(fcBuildings));
    }
    @RequestMapping("/estate/updateBuilding")
    public String updateBuilding(FcBuilding fcBuilding){
        System.out.println("updateBuilding");
        Integer integer = estateService.updateBuilding(fcBuilding);
        if(integer != 0){
            return JSONObject.toJSONString(new ReturnObject("恭喜更新成功"));
        }else{
            return JSONObject.toJSONString(new ReturnObject("更新失败"));
        }
    };
    @RequestMapping("/estate/selectUnit")
    public String selectUnit(@RequestBody UnitMessage[] unitMessages){
        System.out.println("---selectUnit---");
        List<FcUnit> allUnits = new ArrayList<>();
        for (UnitMessage unitMessage : unitMessages) {
            allUnits.addAll(estateService.selectUnit(unitMessage));
        }
        return JSONObject.toJSONString(new ReturnObject(allUnits));
    }
}
