package com.flabedu.blackpostoffice.controller

import com.flabedu.blackpostoffice.commom.annotation.LoginCheck
import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserLoginController(
    private val sessionLoginService: SessionLoginService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody userLoginDto: UserLoginDto) = sessionLoginService.login(userLoginDto)

    @LoginCheck
    @PostMapping("/logout")
    fun logout() = sessionLoginService.logout()
}

