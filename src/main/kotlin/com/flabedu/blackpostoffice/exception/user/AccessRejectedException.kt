package com.flabedu.blackpostoffice.exception.user

import com.flabedu.blackpostoffice.commom.utils.constants.NOT_AUTHORIZED

class AccessRejectedException : RuntimeException(NOT_AUTHORIZED)
