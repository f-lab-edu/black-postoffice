package com.flabedu.blackpostoffice.parkyounghwan.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:parkyounghwan/application-parkyounghwan.yml")
class CustomDataSourceConfig {

    val env: Environment

    @Autowired
    constructor(env: Environment) {
        this.env = env
    }

    @Bean("dataSource")
    fun customDataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(this.env.getProperty("spring.datasource.driver-class-name"))
            .url(this.env.getProperty("spring.datasource.url"))
            .username(this.env.getProperty("spring.datasource.username"))
            .password(this.env.getProperty("spring.datasource.password"))
            .build()
}