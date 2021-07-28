package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.commom.enumeration.Role
import com.flabedu.blackpostoffice.model.user.UserLogin

interface LoginService {

    fun login(userLogin: UserLogin)

    fun invalidLoginCheck(userLogin: UserLogin)

    fun getCurrentUserEmail(): String

    fun getCurrentUserRole(): Role

    fun logout()
}
