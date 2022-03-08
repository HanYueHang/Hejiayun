package com.mashibing.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.bean.FcBuilding;
import com.mashibing.bean.FcEstate;
import com.mashibing.bean.FcUnit;
import com.mashibing.bean.TblCompany;
import com.mashibing.mapper.FcBuildingMapper;
import com.mashibing.mapper.FcEstateMapper;
import com.mashibing.mapper.FcUnitMapper;
import com.mashibing.mapper.TblCompanyMapper;
import com.mashibing.valueobject.UnitMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstateService {
    @Autowired
    private TblCompanyMapper tblCompanyMapper;
    @Autowired
    private FcEstateMapper fcEstateMapper;
    @Autowired
    private FcBuildingMapper fcBuildingMapper;
    @Autowired
    private FcUnitMapper fcUnitMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public List<TblCompany> selectCompany(){
        List<TblCompany> companys = tblCompanyMapper.selectCompany();
        return companys;
    }
    /*
    在插入信息之前最好对当前的信息做判断
    判断住宅编码是否已经存在，
    如果已经存在 则不能插入
     */
    public Integer insertEstate(FcEstate fcEstate){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("estate_code",fcEstate.getEstateCode());
        FcEstate fcEstate1 = fcEstateMapper.selectOne(queryWrapper);
        int insertRows= 0;
        if(fcEstate1 != null){
            insertRows = 0;
        }else{
            insertRows = fcEstateMapper.insert(fcEstate);
        }
        return insertRows;
    };

    /**
     * 先插入数据再查询数据
     * @return
     */
    public List<FcBuilding> selectBuilding(Integer buildNumber,String estateCode){
        List<FcBuilding> fcBuildings = new ArrayList<>();
        for (int i=0;i<buildNumber;i++){
            FcBuilding fcBuilding = new FcBuilding();
            fcBuilding.setBuildingCode("B"+(i+1));
            fcBuilding.setBuildingName("第"+(i+1)+"号楼");
            fcBuilding.setEstateCode(estateCode);
            fcBuildingMapper.insert(fcBuilding);
            fcBuildings.add(fcBuilding);
        }
        return fcBuildings;
    }
    public Integer updateBuilding(FcBuilding fcBuilding){
        int i = fcBuildingMapper.updateById(fcBuilding);
        return i;
    }
    public List<FcUnit> selectUnit(UnitMessage unitMessage){
        List<FcUnit> fcUnits = new ArrayList<>();
        for (int i = 0;i< unitMessage.getUnitCount();i++){
            FcUnit fcUnit = null;
            ValueOperations valueOperations = redisTemplate.opsForValue();
            String buildingCode = unitMessage.getBuildingCode();
            if(redisTemplate.hasKey(buildingCode + "_U" + (i+1))){
                fcUnit = (FcUnit)valueOperations.get(buildingCode + "_U" + (i+1));
                System.out.println("Redis FcUnit:"+fcUnit);
            }else{
                fcUnit = new FcUnit();
                fcUnit.setBuildingCode(buildingCode);
                fcUnit.setUnitCode("U" + (i+1));
                fcUnit.setUnitName("第" + (i+1) + "单元");
                fcUnitMapper.insert(fcUnit);
                redisTemplate.opsForValue().set(buildingCode + "_U" + (i+1),fcUnit);
                System.out.println("New FcUnit:"+fcUnit);
            }
            fcUnits.add(fcUnit);
        }
        return fcUnits;
    }
}
