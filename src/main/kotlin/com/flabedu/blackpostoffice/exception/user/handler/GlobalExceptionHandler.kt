package com.flabedu.blackpostoffice.exception.user.handler

import com.flabedu.blackpostoffice.exception.EmailDuplicateException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(value = [EmailDuplicateException::class])
    fun handleEmailDuplicateException(): ResponseEntity<String> = ResponseEntity.status(HttpStatus.CONFLICT).body("이미 존재하는 이메일 입니다.")

}