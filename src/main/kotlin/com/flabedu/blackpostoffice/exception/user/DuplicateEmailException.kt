package com.flabedu.blackpostoffice.exception.user

import com.flabedu.blackpostoffice.util.logging.logger

class DuplicateEmailException : IllegalArgumentException(MESSAGE) {
    companion object {
        private const val MESSAGE = "이미 존재하는 이메일 입니다."
    }

    init {
        logger.debug(MESSAGE)
    }
}
