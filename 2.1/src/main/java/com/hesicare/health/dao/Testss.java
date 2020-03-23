package com.hesicare.health.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hesicare.health.entity.BloodPressure;
import com.hesicare.health.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface Testss extends BaseMapper<BloodPressure> {
}
