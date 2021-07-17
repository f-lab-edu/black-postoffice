package com.flabedu.blackpostoffice.controller

import com.flabedu.blackpostoffice.commom.annotation.AuthorizedAccessCheck
import com.flabedu.blackpostoffice.controller.dto.user.UserSignUpDto
import com.flabedu.blackpostoffice.domain.model.User.Role.USER
import com.flabedu.blackpostoffice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@Valid @RequestBody userSignUpDto: UserSignUpDto) {
        userService.saveUser(userSignUpDto)
    }

    @AuthorizedAccessCheck(authority = USER)
    @PatchMapping("/my-info")
    fun userInfoUpdate(@RequestPart("profileImage") multipartFile: MultipartFile) {
        userService.userInfoUpdate(multipartFile)
    }

    @AuthorizedAccessCheck(authority = USER)
    @PutMapping("/profile-image")
    fun deleteProfileImage() {
        userService.deleteProfileImage()
    }
}
