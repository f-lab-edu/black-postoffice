package com.flabedu.blackpostoffice.exception.user.handler

import com.flabedu.blackpostoffice.exception.dto.ErrorResponseDto
import com.flabedu.blackpostoffice.exception.user.AccessRejectedException
import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
import com.flabedu.blackpostoffice.exception.user.UnauthorizedLoginException
import com.flabedu.blackpostoffice.commom.utils.constants.DUPLICATED_EMAIL
import com.flabedu.blackpostoffice.commom.utils.constants.INVALID_LOGIN_INFO
import com.flabedu.blackpostoffice.commom.utils.constants.NOT_AUTHORIZED
import com.flabedu.blackpostoffice.commom.logging.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmailException(exception: DuplicateEmailException): ErrorResponseDto {
        logger.debug(DUPLICATED_EMAIL)
        return ErrorResponseDto(exception.message!!)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedLoginException::class)
    fun handleUnauthorizedLoginException(exception: UnauthorizedLoginException): ErrorResponseDto {
        logger.debug(INVALID_LOGIN_INFO)
        return ErrorResponseDto(exception.message!!)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessRejectedException::class)
    fun handleAccessRejectedException(exception: AccessRejectedException): ErrorResponseDto {
        logger.debug(NOT_AUTHORIZED)
        return ErrorResponseDto(exception.message!!)
    }
}
