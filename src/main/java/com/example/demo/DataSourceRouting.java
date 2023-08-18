package com.example.demo;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceRouting extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return MTContextHolder.getMTLabel();
    }

    public void initDatasource() {
        Map<Object, Object> dataSourceMap = new HashMap<>();

        for (int i = 1; i <= 5; i++) {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setUrl("jdbc:postgresql://yugabyte:5433/mtdb?currentSchema=tenant" + i);
            dataSource.setUsername("yugabyte");
            dataSource.setPassword("yugabyte");
            dataSource.setSchema("tenant" + i);

            dataSourceMap.put(i, dataSource);
        }

        this.setTargetDataSources(dataSourceMap);
        this.setDefaultTargetDataSource(dataSourceMap.get(1));
    }
}
