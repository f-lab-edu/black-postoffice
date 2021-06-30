package com.flabedu.blackpostoffice.exception.handler

import com.flabedu.blackpostoffice.commom.logging.Logger
import com.flabedu.blackpostoffice.exception.DuplicateRequestException
import com.flabedu.blackpostoffice.exception.InvalidRequestException
import com.flabedu.blackpostoffice.exception.UnauthorizedAccessException
import com.flabedu.blackpostoffice.exception.dto.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler : Logger {

    companion object : Logger

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateRequestException::class)
    fun handleDuplicateEmailException(exception: DuplicateRequestException): ErrorResponseDto? {
        logger.error(exception.message)
        return exception.message?.let { ErrorResponseDto(it) }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidRequestException::class)
    fun handleInvalidRequestException(exception: InvalidRequestException): ErrorResponseDto? {
        logger.debug(exception.message, exception)
        return exception.message?.let { ErrorResponseDto(it) }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedAccessException::class)
    fun handleUnauthorizedAccessException(exception: UnauthorizedAccessException): ErrorResponseDto? {
        logger.debug(exception.message, exception)
        return exception.message?.let { ErrorResponseDto(it) }
    }
}
