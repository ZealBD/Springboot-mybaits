package com.hesicare.health.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hesicare.health.entity.BloodPressure;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PressureDao  extends BaseMapper<BloodPressure> {
    @Update("update patient_blood_pressure_view set  data_status=#{status} where id=#{id}")
    int updatebyid(@Param("id") Long id, @Param("status") int status);
        }
