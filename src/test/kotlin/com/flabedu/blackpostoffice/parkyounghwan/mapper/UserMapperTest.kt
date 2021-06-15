package com.flabedu.blackpostoffice.parkyounghwan.mapper

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
    @Qualifier("pUserMapper")
    lateinit var userMapper: UserMapper

    @Test
    fun `UserMapperTest 클래스 호출 테스트`() {
        println("hello `UserMapperTest`")
    }

    @Test
    fun `UserMapper 저장 된 유저 ID 로 불러오기`() {
        print(userMapper.selectUserById(1))
    }
}