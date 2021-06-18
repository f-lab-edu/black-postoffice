package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class User(
    val address: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val email: String,

    val nickName: String,

    val password: String,

    val phone: String,

    val userRole: UserRole = UserRole.USER

) {
    private constructor() : this(address = "", email = "", nickName = "", password = "", phone = "")

    enum class UserRole {
        NONE, USER, ADMIN
    }
}
