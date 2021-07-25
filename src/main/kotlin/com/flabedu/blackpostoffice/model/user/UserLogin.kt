package com.flabedu.blackpostoffice.model.user

import javax.validation.constraints.NotBlank

data class UserLogin(

    @field:NotBlank(message = "이메일은 필수 입력입니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수 입력입니다.")
    val password: String,
)
