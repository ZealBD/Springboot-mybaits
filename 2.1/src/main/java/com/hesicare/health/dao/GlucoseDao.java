package com.hesicare.health.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hesicare.health.entity.BloodGlucose;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface GlucoseDao extends BaseMapper<BloodGlucose> {
    @Update("update patient_blood_glucose_view set  data_status=#{status} where id=#{id}")
    int updatebyid(@Param("id") Long id,@Param("status") String status);
}

