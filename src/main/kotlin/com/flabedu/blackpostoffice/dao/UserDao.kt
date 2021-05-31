package com.flabedu.blackpostoffice.dao

import com.flabedu.blackpostoffice.domain.User
import java.sql.*
import kotlin.jvm.Throws

class UserDao {

    val connectionMaker: ConnectionMaker

    constructor(connectionMaker: ConnectionMaker) {
        this.connectionMaker = connectionMaker
    }

    @Throws
    fun add(user: User) {
        val c: Connection = connectionMaker.makeConnection()
        val ps: PreparedStatement = c.prepareStatement("INSERT INTO USERS(id, name, password) VALUES(?, ?, ?)")

        ps.setString(1, user.id)
        ps.setString(2, user.name)
        ps.setString(3, user.password)

        ps.executeUpdate()

        ps.close()
        c.close()
    }

    @Throws
    fun get(id: String): User {

        val c: Connection = connectionMaker.makeConnection()
        val ps: PreparedStatement = c.prepareStatement("SELECT * FROM USERS WHERE id = ?")
        ps.setString(1, id)

        val rs: ResultSet = ps.executeQuery()
        rs.next()

        val user = User()
        user.id = rs.getString("id")
        user.name = rs.getString("name")
        user.password = rs.getString("password")

        rs.close()
        ps.close()
        c.close()

        return user
    }
}