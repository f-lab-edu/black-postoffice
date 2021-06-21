package com.flabedu.blackpostoffice.exception.user.handler

import com.flabedu.blackpostoffice.exception.dto.ErrorResponseDto
import com.flabedu.blackpostoffice.exception.user.AccessRejectedException
import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
import com.flabedu.blackpostoffice.exception.user.UnauthorizedLoginException
import com.flabedu.blackpostoffice.exception.user.UserNotLoginException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmailException(exception: DuplicateEmailException) = ErrorResponseDto(exception.message!!)

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedLoginException::class)
    fun handleEmailNotExistsException(exception: UnauthorizedLoginException) = ErrorResponseDto(exception.message!!)

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UserNotLoginException::class)
    fun handleUserNotLoginException(exception: UserNotLoginException) = ErrorResponseDto(exception.message!!)

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessRejectedException::class)
    fun handleAccessRejectedException(exception: AccessRejectedException) = ErrorResponseDto(exception.message!!)
}
