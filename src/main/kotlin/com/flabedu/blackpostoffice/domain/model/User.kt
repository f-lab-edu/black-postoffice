package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class User(
    val address: String = "",

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val email: String = "",

    val nickName: String = "",

    val password: String = "",

    val phone: String = "",

    val role: Role = Role.USER

) {
    enum class Role {
        NONE, USER, ADMIN
    }
}
