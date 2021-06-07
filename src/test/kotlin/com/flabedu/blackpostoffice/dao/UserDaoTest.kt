package com.flabedu.blackpostoffice.dao

import com.flabedu.blackpostoffice.domain.User
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(properties = ["classpath:application.yml"])
class UserDaoTest {

    @Autowired
    private lateinit var userDao: UserDao

    @Test
    fun `유저 등록 테스드`() {
        val user = User(1, "parkyounghwan", "dudrnxps@gmail.com", "1234")

        userDao.add(user)

        assertEquals(1, user.id)
        assertEquals("parkyounghwan", user.name)
        assertEquals("1234", user.password)

        var user2: User = userDao.get(1)

        assertEquals(user.id, user2.id)
        assertEquals(user.name, user2.name)
        assertEquals(user.password, user2.password)
    }
}