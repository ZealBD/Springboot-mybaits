package com.hesicare;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.hesicare.health.dao")
public class  HealthApplication{
    protected static final Logger LOGGER = LoggerFactory.getLogger(SpringApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
    }
}
