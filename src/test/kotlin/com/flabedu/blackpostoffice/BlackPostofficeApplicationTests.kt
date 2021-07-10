package com.flabedu.blackpostoffice

import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@ConfigurationPropertiesScan("com.flabedu.blackpostoffice.commom.property")
@TestPropertySource("classpath:application-local.yml")
class BlackPostofficeApplicationTests {
    @Test
    fun contextLoads() {
    }
}
