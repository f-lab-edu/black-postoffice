package com.flabedu.blackpostoffice.util.annotation

import com.flabedu.blackpostoffice.domain.model.User

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class LoginCheck(
    val authority: User.UserRole = User.UserRole.NONE
)
