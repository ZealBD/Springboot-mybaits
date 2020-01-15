package com.hesicare.common.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DataSourceAspect {
    protected static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);
    @Before("@annotation(ds)")
    public void beforeDataSource(DataSource ds) {
        DataSourceType value = ds.value();
        DataSourceContextHolder.setDataSource(value);
        LOGGER.info("当前使用的数据源为：{}", value);
    }
    @After("@annotation(ds)")
    public void afterDataSource(DataSource ds){
        DataSourceContextHolder.clearDataSource();
    }
}