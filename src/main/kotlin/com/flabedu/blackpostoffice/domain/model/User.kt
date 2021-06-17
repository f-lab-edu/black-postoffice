package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class User @JvmOverloads constructor(
    val address: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val email: String,

    val nickName: String,

    val password: String,

    val phone: String,

    val userRole: UserRole = UserRole.USER

) {
    enum class UserRole {
        NONE, USER, ADMIN
    }
}
