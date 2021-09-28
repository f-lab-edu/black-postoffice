package com.flabedu.blackpostoffice.controller

import com.flabedu.blackpostoffice.commom.annotation.AuthorizedAccessCheck
import com.flabedu.blackpostoffice.commom.enumeration.Role.USER
import com.flabedu.blackpostoffice.model.user.UserSignUp
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
    fun createUser(@Valid @RequestBody userSignUp: UserSignUp) {
        userService.saveUser(userSignUp)
    }

    @PatchMapping("/my-info")
    @ResponseStatus(HttpStatus.CREATED)
    @AuthorizedAccessCheck(authority = USER)
    fun userInfoUpdate(@RequestPart("profileImage") multipartFile: MultipartFile) {
        userService.userInfoUpdate(multipartFile)
    }

    @PutMapping("/profile-image")
    @ResponseStatus(HttpStatus.OK)
    @AuthorizedAccessCheck(authority = USER)
    fun deleteProfileImage() {
        userService.deleteProfileImage()
    }
}
