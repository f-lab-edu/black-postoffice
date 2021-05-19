package com.flabedu.blackpostoffice.domain.user.repository

import com.flabedu.blackpostoffice.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun existsByEmail(email: String): Boolean

}