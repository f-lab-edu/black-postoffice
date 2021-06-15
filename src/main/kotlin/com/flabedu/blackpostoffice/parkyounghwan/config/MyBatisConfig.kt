package com.flabedu.blackpostoffice.parkyounghwan.config

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

@Configuration
class MyBatisConfig {

    @Bean
    fun sqlSessionFactory(dataSource: DataSource): SqlSessionFactory? {
        val factoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(dataSource)

        val resource = PathMatchingResourcePatternResolver().getResource("classpath:parkyounghwan/mapper/UserMapper.xml")

        factoryBean.setMapperLocations(resource)

        return factoryBean.getObject()
    }
}