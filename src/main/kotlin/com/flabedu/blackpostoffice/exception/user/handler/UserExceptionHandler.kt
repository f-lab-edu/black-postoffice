package com.flabedu.blackpostoffice.exception.user.handler

import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
import com.flabedu.blackpostoffice.exception.user.UnauthorizedLoginException
import com.flabedu.blackpostoffice.exception.user.UserNotLoginException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserExceptionHandler {

    companion object {
        val logger: Logger = LogManager.getLogger(UserExceptionHandler::class)
    }

    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmailException(): ResponseEntity<String> {
        logger.debug("이미 존재하는 이메일 입니다.")
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일 입니다.")
    }

    @ExceptionHandler(UnauthorizedLoginException::class)
    fun handleEmailNotExistsException(): ResponseEntity<String> {
        logger.debug("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디가 혹은 비밀번호가 틀렸습니다.")
    }

    @ExceptionHandler(UserNotLoginException::class)
    fun handleUserNotLoginException(): ResponseEntity<String> {
        logger.debug("로그인 후에 이용 가능합니다.")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후에 이용 가능합니다.")
    }
}
