package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.commom.encryption.Sha256Encryption
import com.flabedu.blackpostoffice.commom.utils.constants.LOGIN_MY_EMAIL
import com.flabedu.blackpostoffice.commom.utils.constants.MY_ROLE
import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import com.flabedu.blackpostoffice.domain.model.User.Role.ADMIN
import com.flabedu.blackpostoffice.domain.model.User.Role.USER
import com.flabedu.blackpostoffice.exception.InvalidRequestException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class SessionLoginService(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption,
    private val session: HttpSession,
) : LoginService {

    override fun login(userLoginDto: UserLoginDto) {
        invalidLoginCheck(userLoginDto)
        setSessionAttribute(userLoginDto)
    }

    override fun invalidLoginCheck(userLoginDto: UserLoginDto) {
        val loginCheckEmail = userMapper.getUserByEmail(userLoginDto.email)
        val loginCheckPassword = sha256Encryption.encryption(userLoginDto.password)
        val myPassword = userMapper.getPasswordByEmail(userLoginDto.email)

        if (loginCheckEmail == null || (loginCheckPassword != myPassword)) {
            throw InvalidRequestException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")
        }
    }

    private fun setSessionAttribute(userLoginDto: UserLoginDto) {
        val userRole = userMapper.getUserRoleByEmail(userLoginDto.email)

        session.setAttribute(LOGIN_MY_EMAIL, userLoginDto.email)

        when (userRole) {
            USER.name -> session.setAttribute(MY_ROLE, USER)
            ADMIN.name -> session.setAttribute(MY_ROLE, ADMIN)
        }
    }
}
