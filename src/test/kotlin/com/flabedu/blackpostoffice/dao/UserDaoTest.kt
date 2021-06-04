package com.flabedu.blackpostoffice.dao

import com.flabedu.blackpostoffice.domain.User
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration
@TestPropertySource("/application.yml")
class UserDaoTest {

    @Test
    fun `유저 등록 테스드`() {
        val context: ApplicationContext = AnnotationConfigApplicationContext(DaoFactory::class.java)
        val dao: UserDao = context.getBean("userDao", UserDao::class.java)

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