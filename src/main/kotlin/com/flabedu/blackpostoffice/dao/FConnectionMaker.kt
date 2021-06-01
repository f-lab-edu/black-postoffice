package com.flabedu.blackpostoffice.dao

import java.sql.Connection
import java.sql.DriverManager
import kotlin.jvm.Throws

class FConnectionMaker: ConnectionMaker {

    @Throws
    override fun makeConnection(): Connection {
        Class.forName("org.h2.Driver")

        return DriverManager.getConnection("jdbc:h2:mem:test", "sa", "")
    }
}