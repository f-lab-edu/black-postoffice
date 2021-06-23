package com.flabedu.blackpostoffice.exception.user

import com.flabedu.blackpostoffice.commom.utils.constants.DUPLICATED_EMAIL

class DuplicateEmailException : IllegalArgumentException(DUPLICATED_EMAIL)
