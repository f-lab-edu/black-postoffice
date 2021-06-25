package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserDto
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import com.flabedu.blackpostoffice.commom.encryption.Sha256Encryption
import com.flabedu.blackpostoffice.exception.DuplicateRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption
) {

    @Transactional
    fun saveUser(userDto: UserDto) {

        val user = UserDto(
            userDto.email,
            sha256Encryption.encryption(userDto.password),
            userDto.nickName,
            userDto.address,
            userDto.phone,
            userDto.createdAt
        )

        duplicateEmailCheck(userDto.email)
        userMapper.save(user.toUserEntity())
    }

    fun duplicateEmailCheck(email: String) {
        if (userMapper.isExistsByEmail(email)) {
            throw DuplicateRequestException("이미 존재하는 이메일 입니다.")
        }
    }
}
