package com.flabedu.blackpostoffice.dao

import java.sql.Connection
import kotlin.jvm.Throws

interface ConnectionMaker {
    @Throws
    fun makeConnection(): Connection
}