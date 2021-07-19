package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class User(

    val userId: Long? = null,

    val address: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val email: String? = null,

    val nickName: String? = null,

    val password: String? = null,

    val phone: String? = null,

    val role: Role = Role.USER,

    val profileImagePath: String? = null

) {
    enum class Role {
        NONE, USER, ADMIN
    }
}
