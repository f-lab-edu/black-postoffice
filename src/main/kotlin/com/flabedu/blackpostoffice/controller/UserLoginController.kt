package com.flabedu.blackpostoffice.controller

import com.flabedu.blackpostoffice.commom.annotation.AuthorizedAccessCheck
import com.flabedu.blackpostoffice.commom.enumeration.Role.USER
import com.flabedu.blackpostoffice.model.user.UserLogin
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserLoginController(
    private val sessionLoginService: SessionLoginService
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun login(@Valid @RequestBody userLogin: UserLogin) {
        sessionLoginService.login(userLogin)
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @AuthorizedAccessCheck(authority = USER)
    fun logout() {
        sessionLoginService.logout()
    }
}
