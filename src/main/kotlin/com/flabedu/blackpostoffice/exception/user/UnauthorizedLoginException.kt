package com.flabedu.blackpostoffice.exception.user

import com.flabedu.blackpostoffice.commom.utils.constants.INVALID_LOGIN_INFO

class UnauthorizedLoginException : RuntimeException(INVALID_LOGIN_INFO)
