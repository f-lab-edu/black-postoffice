package com.flabedu.blackpostoffice

import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.sql.*

@RunWith(SpringJUnit4ClassRunner::class)
class H2Test {

    @Test
    fun `H2Database 연결 확인`() {
        Class.forName("org.h2.Driver")

        val c: Connection = DriverManager.getConnection("jdbc:h2:mem:test", "SA", "")
        assertEquals(c.isClosed, false)

        c.close()

        assertEquals(c.isClosed, true)
    }
}