package com.flabedu.blackpostoffice.util.annotation

import com.flabedu.blackpostoffice.domain.model.User.Role
import com.flabedu.blackpostoffice.domain.model.User.Role.NONE

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class LoginCheck(
    val authority: Role = NONE
)
