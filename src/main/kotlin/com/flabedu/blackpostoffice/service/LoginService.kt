package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.domain.model.User

interface LoginService {

    fun login(userLoginDto: UserLoginDto)

    fun loginCheck(userLoginDto: UserLoginDto)

    fun getCurrentUserEmail(): String

    fun getCurrentUserRole(): User.Role
}
