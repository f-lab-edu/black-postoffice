package com.flabedu.blackpostoffice

import com.flabedu.blackpostoffice.domain.User
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
class UserFieldCheckTest {

    @Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun `User Field 값 유효성 검증 테스트`() {
//        expectedException.expect(Exception::class.java)
//        expectedException.expectMessage("java.lang.Exception: INVALIDNAMEVALUE")

        val user = User()

        user.name = "123456712311"
//        user.email = "parkyounghwan@gmailcom"
//        user.password = "12parkyoung"
    }
}