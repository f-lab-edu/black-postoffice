package com.flabedu.blackpostoffice.controller.dto

import com.flabedu.blackpostoffice.domain.model.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UserSignUpDto(

    @field:Email(message = "올바른 이메일 주소를 입력해주세요.")
    @field:NotBlank(message = "이메일은 필수 입력입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수 입력입니다.")
    @field:Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    val password: String,

    @field:NotBlank(message = "닉네임은 필수 입력입니다.")
    @field:Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하로 입력해주세요.")
    val nickName: String,

    @field:NotBlank(message = "주소는 필수 입력입니다.")
    val address: String,

    @field:NotBlank(message = "휴대폰 번호는 필수 입력입니다.")
    @field:Pattern(regexp = "010-[0-9]{3,4}-[0-9]{4}", message = "올바른 휴대폰 번호를 입력해주세요.")
    val phone: String,

    val profileImagePath: String?

) {
    fun toUserEntity(password: String) =
        User(
            email = email,
            password = password,
            nickName = nickName,
            address = address,
            phone = phone,
            profileImagePath = profileImagePath
        )
}
