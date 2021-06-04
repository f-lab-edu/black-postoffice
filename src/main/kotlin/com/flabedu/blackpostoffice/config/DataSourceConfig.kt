package com.flabedu.blackpostoffice.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig

@Bean
@ConfigurationProperties(prefix = "spring.datasource.hikari")
fun dataSourceProperties(): DataSourceProperties {
    return DataSourceProperties()
}

@Bean
fun dataSource(properties: DataSourceProperties): DataSource {
    return properties.initializeDataSourceBuilder()
        .build()
}