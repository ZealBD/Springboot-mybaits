package com.hesicare.health.services.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hesicare.health.dao.PressureDao;
import com.hesicare.health.dao.Testss;
import com.hesicare.health.entity.BloodPressure;
import com.hesicare.health.entity.SysDept;
import com.hesicare.health.mappering.TripartiteDeviceMapper;
import com.hesicare.health.services.testcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Testccimpl{
@Autowired
  //  private Testss testss;
private PressureDao dao;
//@DS("slave_1")
public  List<BloodPressure> sel(){
    List<BloodPressure> bloodPressures = dao.selectList(null);
    System.out.println(bloodPressures);
    return  dao.selectList(null);
}
}
