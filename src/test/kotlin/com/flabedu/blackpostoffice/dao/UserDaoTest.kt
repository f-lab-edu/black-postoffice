package com.flabedu.blackpostoffice.dao

import com.flabedu.blackpostoffice.domain.User
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(properties = ["classpath:application.properties"])
class UserDaoTest {

    @Test
    fun `유저 등록 테스드`() {
        val dao: UserDao = DaoFactory().userDao()
        val user = User()

        user.id = "dudrnxps"
        user.name = "박영환"
        user.password = "1234"

        dao.add(user)

        assertEquals(user.id, "dudrnxps")

        val user2: User = dao.get(user.id)

        assertEquals(user.id, user2.id)
        assertEquals(user.password, user2.password)
    }
}