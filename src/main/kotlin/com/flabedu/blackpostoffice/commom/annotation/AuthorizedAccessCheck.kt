package com.flabedu.blackpostoffice.commom.annotation

import com.flabedu.blackpostoffice.domain.model.User.Role
import com.flabedu.blackpostoffice.domain.model.User.Role.NONE

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AuthorizedAccessCheck(
    val authority: Role = NONE
)
