package com.flabedu.blackpostoffice

import com.flabedu.blackpostoffice.commom.property.AwsProperties
import com.flabedu.blackpostoffice.commom.property.RedisProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(value = [AwsProperties::class, RedisProperties::class])
class BlackPostofficeApplication

fun main(args: Array<String>) {

    runApplication<BlackPostofficeApplication>(*args)
}
