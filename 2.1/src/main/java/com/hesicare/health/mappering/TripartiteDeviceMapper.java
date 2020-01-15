package com.hesicare.health.mappering;

import com.hesicare.common.datasource.DataSource;
import com.hesicare.common.datasource.DataSourceType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository

public interface TripartiteDeviceMapper {
    @DataSource(value = DataSourceType.db2)
    List<Map> sela();
    @DataSource(value = DataSourceType.db1)
    List<Map> selaa();
}
