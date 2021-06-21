package com.flabedu.blackpostoffice.exception.user

import com.flabedu.blackpostoffice.util.logging.logger

class UserNotLoginException : RuntimeException(MESSAGE) {
    companion object {
        private const val MESSAGE = "로그인 후에 이용 가능합니다."
    }

    init {
        logger.debug(MESSAGE)
    }
}
