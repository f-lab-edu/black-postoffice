package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserLoginDto

interface LoginService {

    fun login(userLoginDto: UserLoginDto)

    fun invalidLoginCheck(userLoginDto: UserLoginDto)

    fun logout()
}
