package com.flabedu.blackpostoffice.controller.dto.user

import com.flabedu.blackpostoffice.domain.model.User
import org.springframework.web.multipart.MultipartFile

data class UserInfoUpdateDto(
    val profileImagePath: MultipartFile,
) {
    fun toUserInfoUpdate(email: String, profileImage: String) =
        User(
            email = email,
            profileImagePath = profileImage
        )
}
