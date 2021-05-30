package com.flabedu.blackpostoffice.dao

import com.flabedu.blackpostoffice.domain.User
import java.sql.*
import kotlin.jvm.Throws

class UserDao {

    @Throws
    fun add(user: User): Unit {
        Class.forName("org.h2.Driver")

        val conn = DriverManager.getConnection("jdbc:h2:mem:test", "SA", "")
        var ps: PreparedStatement = conn.prepareStatement("CREATE TABLE USERS(id varchar(10) primary key, name varchar(20) not null, password varchar(10) not null)")
        ps.executeUpdate()

        ps = conn.prepareStatement("INSERT INTO USERS(id, name, password) VALUES(?, ?, ?)")
        ps.setString(1, user.id)
        ps.setString(2, user.name)
        ps.setString(3, user.password)

        ps.executeUpdate()

        ps.close()
        conn.close()
    }

    @Throws
    fun get(id: String): User {
        Class.forName("org.h2.Driver")

        val c: Connection = DriverManager.getConnection("jdbc:h2:mem:test", "SA", "")
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