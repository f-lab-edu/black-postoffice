package com.flabedu.blackpostoffice.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.lang.IllegalArgumentException

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class UserValidationCheckTest {

    @Test
    fun `User 'name' field 유효성 검사`() {
        val e: Exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            val user = User()
            user.name = ""
        }

        Assertions.assertEquals("INVALID NAME VALUE", e.message)
    }

    @Test
    fun `User 'email' field 유효성 검사`() {
        val e: Exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            val user = User()
            user.email = ""
        }

        Assertions.assertEquals("INVALID EMAIL VALUE", e.message)
    }

    @Test
    fun `User 'password' field 유효성 검사`() {
        val e: Exception = Assertions.assertThrows(IllegalArgumentException::class.java) {
            val user = User()
            user.password = ""
        }

        Assertions.assertEquals("INVALID PASSWORD VALUE", e.message)
    }
}