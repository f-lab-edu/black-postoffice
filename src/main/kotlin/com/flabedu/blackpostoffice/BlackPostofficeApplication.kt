package com.flabedu.blackpostoffice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.flabedu.blackpostoffice.commom.property")
class BlackPostofficeApplication

fun main(args: Array<String>) {

    runApplication<BlackPostofficeApplication>(*args)
}
