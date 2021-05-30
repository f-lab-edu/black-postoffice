package com.flabedu.blackpostoffice.exception.user.handler

import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
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

    @ExceptionHandler(value = [DuplicateEmailException::class])
    fun handleEmailDuplicateException(): ResponseEntity<String> {
        logger.debug("이미 존재하는 이메일 입니다.")
        return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일 입니다.")
    }
}
