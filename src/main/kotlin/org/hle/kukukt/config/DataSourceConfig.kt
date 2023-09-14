package org.hle.kukukt.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun mariadbDataSourceProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    fun mariadbDataSource(): DataSource {
        return mariadbDataSourceProperties().initializeDataSourceBuilder().build()
    }
}
