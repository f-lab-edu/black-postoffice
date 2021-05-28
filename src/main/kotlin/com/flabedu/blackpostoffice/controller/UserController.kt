package com.flabedu.blackpostoffice.controller

import com.flabedu.blackpostoffice.controller.dto.UserDto
import com.flabedu.blackpostoffice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@Valid @RequestBody userDto: UserDto) {
        userService.saveUser(userDto)
    }
}
