package com.flabedu.blackpostoffice

import com.flabedu.blackpostoffice.dao.UserDao
import com.flabedu.blackpostoffice.domain.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlackPostofficeApplication

fun main(args: Array<String>) {
    runApplication<BlackPostofficeApplication>(*args)

    val userDao = UserDao()
    val user = User("dudrnxps", "박영환", "1234")

    userDao.add(user)

    val result = userDao.get("dudrnxps")

    print(result.toString())
}
