package com.flabedu.blackpostoffice.exception.user

import com.flabedu.blackpostoffice.util.logging.logger

class AccessRejectedException : RuntimeException(MESSAGE) {
    companion object {
        private const val MESSAGE = "이 페이지에 엑세스 하는데 필요한 권한이 존재하지 않습니다."
    }

    init {
        logger.debug(MESSAGE)
    }
}
