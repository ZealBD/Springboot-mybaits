package com.hesicare.health.mappering;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hesicare.health.entity.SysDept;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TripartiteDeviceMapper extends BaseMapper<SysDept> {
    /*  @DataSource(value = DataSourceType.db2)*/
  /*  List<Map> sela();
    List<Map> selaa();
    *//*
    * 查询血压数据，接口推送之后修改状态
    * 状态为4 则数据推送失败或者数据不符合要求
    * *//*
    List<SysDept> SelPressureByState();
    void  UpPressureById(Long id,int state);

    *//*
     * 查询血糖数据，接口推送之后修改状态
     * *//*
    List<BloodGlucose> SelGlucoseByState();
    void  UpGlucoseById(Long id,int state);

    *//*
     * 查询身高体重数据，接口推送之后修改状态
     * *//*
    List<PatientBmiView> SelBmiByState();
    void  UpBmiById(Long id,int state);*/
}
