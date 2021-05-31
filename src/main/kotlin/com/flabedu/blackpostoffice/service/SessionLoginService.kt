package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import com.flabedu.blackpostoffice.exception.user.EmailNotExistsException
import com.flabedu.blackpostoffice.exception.user.WrongPasswordException
import com.flabedu.blackpostoffice.util.encryption.Sha256Encryption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpSession

@Service
class SessionLoginService @Autowired constructor(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption,
    private val session: HttpSession
) : LoginService {

    override fun login(userLoginDto: UserLoginDto) {
        loginEmailCheck(userLoginDto).loginPasswordCheck(userLoginDto)
        session.setAttribute("loginUser", userLoginDto.email)
    }

    fun loginEmailCheck(userLoginDto: UserLoginDto) {
        if (userMapper.getUserByEmail(userLoginDto.email) == null) {
            throw EmailNotExistsException()
        }
    }

    fun Unit.loginPasswordCheck(userLoginDto: UserLoginDto) {
        if (sha256Encryption.encryption(userLoginDto.password) != userMapper.getPasswordByEmail(userLoginDto.email)) {
            throw WrongPasswordException()
        }
    }
}
