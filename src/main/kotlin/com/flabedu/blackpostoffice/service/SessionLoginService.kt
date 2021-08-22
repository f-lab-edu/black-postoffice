package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.commom.encryption.Sha256Encryption
import com.flabedu.blackpostoffice.commom.enumeration.Role
import com.flabedu.blackpostoffice.commom.enumeration.Role.ADMIN
import com.flabedu.blackpostoffice.commom.enumeration.Role.USER
import com.flabedu.blackpostoffice.commom.utils.constants.LOGIN_MY_EMAIL
import com.flabedu.blackpostoffice.commom.utils.constants.MY_ROLE
import com.flabedu.blackpostoffice.exception.InvalidRequestException
import com.flabedu.blackpostoffice.exception.UnauthorizedAccessException
import com.flabedu.blackpostoffice.mapper.UserMapper
import com.flabedu.blackpostoffice.model.user.UserLogin
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class SessionLoginService(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption,
    private val session: HttpSession,
) : LoginService {

    override fun login(userLogin: UserLogin) {
        invalidLoginCheck(userLogin)
        setSessionAttribute(userLogin)
    }

    override fun invalidLoginCheck(userLogin: UserLogin) {
        val loginCheckPassword = sha256Encryption.encryption(userLogin.password)
        val myPassword = userMapper.getPasswordByEmail(userLogin.email)

        if (loginCheckPassword != myPassword) {
            throw InvalidRequestException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")
        }
    }

    override fun logout() = session.invalidate()

    override fun getCurrentUserEmail() =
        (session.getAttribute(LOGIN_MY_EMAIL) ?: throw UnauthorizedAccessException("로그인 후에 이용 가능합니다.")) as String

    override fun getCurrentUserRole() = session.getAttribute(MY_ROLE) as Role

    private fun setSessionAttribute(userLogin: UserLogin) {
        val userRole = userMapper.getUserRoleByEmail(userLogin.email)

        session.setAttribute(LOGIN_MY_EMAIL, userLogin.email)

        when (userRole) {
            USER.name -> session.setAttribute(MY_ROLE, USER)
            ADMIN.name -> session.setAttribute(MY_ROLE, ADMIN)
        }
    }
}
