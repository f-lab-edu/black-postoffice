package com.flabedu.blackpostoffice.exception.user

import com.flabedu.blackpostoffice.util.logging.logger

class UnauthorizedLoginException : RuntimeException(MESSAGE) {
    companion object {
        private const val MESSAGE = "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."
    }

    init {
        logger.debug(MESSAGE)
    }
}
