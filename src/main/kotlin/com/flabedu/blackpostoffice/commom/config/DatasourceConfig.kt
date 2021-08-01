package com.flabedu.blackpostoffice.commom.config

import com.flabedu.blackpostoffice.commom.utils.constants.MASTER
import com.flabedu.blackpostoffice.commom.utils.constants.SLAVE
import com.zaxxer.hikari.HikariDataSource
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class DatasourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    fun masterDataSource() = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave")
    fun slaveDataSource() = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

    @Bean
    fun routingDataSource(
        @Qualifier("masterDataSource") masterDataSource: DataSource,
        @Qualifier("slaveDataSource") slaveDataSource: DataSource,

    ) = RoutingDataSourceConfig().apply {

        setTargetDataSources(
            HashMap<Any, Any>().apply {
                this[MASTER] = masterDataSource
                this[SLAVE] = slaveDataSource
            }
        )
        setDefaultTargetDataSource(masterDataSource)
    }

    @Bean
    @Primary
    fun dataSource(@Qualifier("routingDataSource") routingDataSource: DataSource) =
        LazyConnectionDataSourceProxy(routingDataSource)

    @Bean
    fun transactionManager(@Qualifier("dataSource") dataSource: DataSource) = DataSourceTransactionManager(dataSource)

    @Bean
    fun sqlSessionTemplate() = SqlSessionTemplate(sqlSessionFactory())

    @Bean
    fun sqlSessionFactory() = SqlSessionFactoryBean().apply {
        setDataSource(dataSource(routingDataSource(masterDataSource(), slaveDataSource())))
    }.`object`
}
