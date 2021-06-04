package com.flabedu.blackpostoffice.dao

import com.flabedu.blackpostoffice.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import javax.sql.DataSource

@Repository
class UserDao {

    @Autowired
    private lateinit var dataSource: DataSource
    private lateinit var user: User

    @Throws
    fun add(user: User) {
        val connection: Connection = dataSource.connection
        val ps: PreparedStatement = connection.prepareStatement("INSERT INTO USERS(id, name, password) VALUES(?, ?, ?)")

        ps.setInt(1, user.id)
        ps.setString(2, user.name)
        ps.setString(3, user.password)

        ps.executeUpdate()

        ps.close()
        connection.close()
    }

    @Throws
    fun get(id: Int): User {
        val connection: Connection = dataSource.connection
        val ps: PreparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE id = ?")
        ps.setInt(1, id)

        val rs: ResultSet = ps.executeQuery()
        rs.next()

        this.user = User()
        user.id = rs.getInt("id")
        user.name = rs.getString("name")
        user.password = rs.getString("password")

        rs.close()
        ps.close()
        connection.close()

        return user
    }
}