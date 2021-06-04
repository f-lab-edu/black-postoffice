package com.flabedu.blackpostoffice

import com.flabedu.blackpostoffice.dao.DaoFactory
import com.flabedu.blackpostoffice.dao.UserDao
import com.flabedu.blackpostoffice.domain.User
import junit.framework.Assert
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext

@SpringBootApplication
class BlackPostofficeApplication

fun main(args: Array<String>) {
    runApplication<BlackPostofficeApplication>(*args)

    val context: ApplicationContext = AnnotationConfigApplicationContext(DaoFactory::class.java)
    val dao: UserDao = context.getBean("userDao", UserDao::class.java)

    val user = User()

    user.id = "dudrnxps"
    user.name = "박영환"
    user.password = "1234"

    dao.add(user)

    val user2: User = dao.get(user.id)

    print(user.id)
    print(user2.id)
}
