package com.flabedu.blackpostoffice.controller

import com.flabedu.blackpostoffice.controller.dto.UserDto
import com.flabedu.blackpostoffice.service.UserService
import com.flabedu.blackpostoffice.util.annotation.LoginCheck
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck
    fun createUser(@Valid @RequestBody userDto: UserDto) = userService.saveUser(userDto)
}
