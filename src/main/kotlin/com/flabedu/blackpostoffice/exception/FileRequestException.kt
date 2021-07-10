package com.flabedu.blackpostoffice.exception

import java.lang.RuntimeException

class FileRequestException : RuntimeException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}
