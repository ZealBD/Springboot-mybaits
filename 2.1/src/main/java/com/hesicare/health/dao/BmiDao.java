package com.hesicare.health.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hesicare.health.entity.BloodGlucose;
import com.hesicare.health.entity.PatientBmiView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface BmiDao  extends BaseMapper<PatientBmiView> {
    @Update("update patient_bmi_viewset  data_status=#{status} where id=#{id}")
    int updatebyid(@Param("id") Long id, @Param("status") int status);
}
