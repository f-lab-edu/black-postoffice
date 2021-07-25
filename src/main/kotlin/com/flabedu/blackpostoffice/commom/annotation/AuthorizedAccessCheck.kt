package com.flabedu.blackpostoffice.commom.annotation

import com.flabedu.blackpostoffice.model.user.UserSignUp.Role
import com.flabedu.blackpostoffice.model.user.UserSignUp.Role.NONE

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AuthorizedAccessCheck(
    val authority: Role = NONE
)
