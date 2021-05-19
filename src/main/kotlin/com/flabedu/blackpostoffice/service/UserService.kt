package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.UserDto
import com.flabedu.blackpostoffice.domain.user.repository.UserRepository
import com.flabedu.blackpostoffice.exception.EmailDuplicateException
import com.flabedu.blackpostoffice.util.encryption.Sha256Encryption
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService @Autowired constructor(

    private val userRepository: UserRepository,
    private val sha256Encryption: Sha256Encryption

) {

    @Transactional
    fun saveUser(userDto: UserDto) {
        emailDuplicateCheck(userDto.email)
        userRepository.save(userDto.toUserEntity(sha256Encryption))
    }

    fun emailDuplicateCheck(email: String) {
        if (userRepository.existsByEmail(email)) {
            throw EmailDuplicateException()
        }
    }
}