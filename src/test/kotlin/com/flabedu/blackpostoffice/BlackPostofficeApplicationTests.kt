package com.flabedu.blackpostoffice

import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration

@SpringBootTest
@ConfigurationPropertiesScan("com.flabedu.blackpostoffice.commom.property")
@TestConfiguration
class BlackPostofficeApplicationTests {
    @Test
    fun contextLoads() {
    }
}
