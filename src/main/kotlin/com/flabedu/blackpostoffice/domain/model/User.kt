package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class User(
    val address: String,

    val createdAt: LocalDateTime,

    val email: String,

    val nickName: String,

    val password: String,

    val phone: String
)