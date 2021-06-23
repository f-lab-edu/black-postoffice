package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserDto
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
import com.flabedu.blackpostoffice.util.encryption.Sha256Encryption
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption
) {

    @Transactional
    fun saveUser(userDto: UserDto) {

        duplicateEmailCheck(userDto.email)

        userMapper.save(userDto.toUserEntity(sha256Encryption.encryption(userDto.password)))
    }

    fun duplicateEmailCheck(email: String) {
        if (userMapper.isExistsByEmail(email)) {
            throw DuplicateEmailException()
        }
    }
}
