package com.flabedu.blackpostoffice.commom.annotation

import com.flabedu.blackpostoffice.commom.enumeration.Role
import com.flabedu.blackpostoffice.commom.enumeration.Role.NONE

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class AuthorizedAccessCheck(
    val authority: Role = NONE
)
