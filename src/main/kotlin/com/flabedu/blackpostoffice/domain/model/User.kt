package com.flabedu.blackpostoffice.domain.model

import com.flabedu.blackpostoffice.controller.dto.UserDto
import java.time.LocalDateTime

class User(
    val address: String,

    val createdAt: LocalDateTime,

    val email: String,

    val nickName: String,

    val password: String,

    val phone: String,

    val userRole: UserDto.UserRole

)
