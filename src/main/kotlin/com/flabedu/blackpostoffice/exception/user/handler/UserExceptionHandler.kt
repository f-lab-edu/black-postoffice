package com.flabedu.blackpostoffice.exception.user.handler

import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
import com.flabedu.blackpostoffice.exception.user.EmailNotExistsException
import com.flabedu.blackpostoffice.exception.user.UserNotLoginException
import com.flabedu.blackpostoffice.exception.user.WrongPasswordException
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

    @ExceptionHandler(EmailNotExistsException::class)
    fun handleEmailNotExistsException(): ResponseEntity<String> {
        logger.debug("가입되지 않은 이메일입니다.")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("가입되지 않은 이메일입니다.")
    }

    @ExceptionHandler(WrongPasswordException::class)
    fun handleWrongPasswordException(): ResponseEntity<String> {
        logger.debug("일치하지 않는 비밀번호 입니다.")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("일치하지 않는 비밀번호 입니다.")
    }

    @ExceptionHandler(UserNotLoginException::class)
    fun handleUserNotLoginException(): ResponseEntity<String> {
        logger.debug("로그인 후에 이용 가능합니다.")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후에 이용 가능합니다.")
    }
}
