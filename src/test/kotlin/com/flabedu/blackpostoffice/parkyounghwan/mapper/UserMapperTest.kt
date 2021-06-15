package com.flabedu.blackpostoffice.parkyounghwan.mapper

import com.flabedu.blackpostoffice.parkyounghwan.domain.User
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class UserMapperTest {

    @Autowired
    @Qualifier("parkyounghwan.UserMapper")
    lateinit var userMapper: UserMapper

    @Test
    fun `회원 정보 저장 테스트`() {
        val user = User(0, "홍길동", "honggildong@korea.com", "1234")

        assertEquals(0, userMapper.deleteAllUser())
        assertEquals(1, userMapper.insertUser(user))

        val result: User = userMapper.selectUserByEmail("honggildong@korea.com")

        assertEquals("홍길동", result.name)
        assertEquals(1, userMapper.deleteAllUser())
    }
}