package com.flabedu.blackpostoffice.dao

import com.flabedu.blackpostoffice.domain.User
import java.sql.*
import javax.sql.DataSource
import kotlin.jvm.Throws

class UserDao {

    private lateinit var dataSource: DataSource
    private lateinit var c: Connection
    private lateinit var ps: PreparedStatement

    fun setDataSource(dataSource: DataSource) {
        this.dataSource = dataSource
    }

    @Throws
    fun add(user: User) {
        this.c = dataSource.connection
        this.ps = this.c.prepareStatement("INSERT INTO USERS(id, name, password) VALUES(?, ?, ?)")

        this.ps.setString(1, user.id)
        this.ps.setString(2, user.name)
        this.ps.setString(3, user.password)

        this.ps.executeUpdate()

        this.ps.close()
        this.c.close()
    }

    @Throws
    fun get(id: String): User {
        this.c = dataSource.connection
        this.ps = this.c.prepareStatement("SELECT * FROM USERS WHERE id = ?")

        this.ps.setString(1, id)

        val rs: ResultSet = ps.executeQuery()
        rs.next()

        val user = User()
        user.id = rs.getString("id")
        user.name = rs.getString("name")
        user.password = rs.getString("password")

        rs.close()
        this.ps.close()
        this.c.close()

        return user
    }
}