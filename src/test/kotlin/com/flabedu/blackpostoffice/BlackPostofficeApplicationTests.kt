package com.flabedu.blackpostoffice

import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ConfigurationPropertiesScan("com.flabedu.blackpostoffice.commom.property")
@TestPropertySource("classpath:application.yml")
class BlackPostofficeApplicationTests {
    @Test
    fun contextLoads() {
    }
}
