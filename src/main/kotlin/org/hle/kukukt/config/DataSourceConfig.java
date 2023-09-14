package org.hle.kukukt.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties mariadbDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mariadbDataSource() {
        return mariadbDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
