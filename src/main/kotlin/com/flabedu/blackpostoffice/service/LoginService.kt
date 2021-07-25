package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.model.user.UserLogin
import com.flabedu.blackpostoffice.commom.enumeration.Role

interface LoginService {

    fun login(userLogin: UserLogin)

    fun invalidLoginCheck(userLogin: UserLogin)

    fun getCurrentUserEmail(): String

    fun getCurrentUserRole(): Role

    fun logout()
}
