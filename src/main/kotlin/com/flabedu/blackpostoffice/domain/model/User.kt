package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class User(

    val email: String,

    val password: String,

    val nickName: String,

    val address: String,

    val phone: String,

    val createdAt: LocalDateTime
)
