package com.flabedu.blackpostoffice.commom.logging

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

interface Logger {
    val logger: Logger
        get() = LogManager.getLogger(this)
}