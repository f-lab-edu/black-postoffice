package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserDto
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
import com.flabedu.blackpostoffice.util.encryption.Sha256Encryption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService @Autowired constructor(
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
            throw DuplicateEmailException()
        }
    }
}
