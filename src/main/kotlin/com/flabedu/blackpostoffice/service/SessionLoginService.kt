package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import com.flabedu.blackpostoffice.exception.user.UnauthorizedLoginException
import com.flabedu.blackpostoffice.util.encryption.Sha256Encryption
import com.flabedu.blackpostoffice.util.interceptor.LOGIN_MY_EMAIL
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class SessionLoginService(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption,
    private val session: HttpSession
) : LoginService {

    override fun login(userLoginDto: UserLoginDto) {
        loginCheck(userLoginDto)
        session.setAttribute(LOGIN_MY_EMAIL, userLoginDto.email)
    }

    override fun loginCheck(userLoginDto: UserLoginDto) {
        if (userMapper.getUserByEmail(userLoginDto.email) == null ||
            sha256Encryption.encryption(userLoginDto.password) != userMapper.getPasswordByEmail(userLoginDto.email)
        ) {
            throw UnauthorizedLoginException()
        }
    }

    override fun logout() = session.invalidate()
}
