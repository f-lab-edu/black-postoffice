package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.user.UserLoginDto
import com.flabedu.blackpostoffice.domain.model.User

interface LoginService {

    fun login(userLoginDto: UserLoginDto)

    fun invalidLoginCheck(userLoginDto: UserLoginDto)

    fun getCurrentUserEmail(): String

    fun getCurrentUserRole(): User.Role

    fun logout()
}
