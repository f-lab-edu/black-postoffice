package com.flabedu.blackpostoffice.dao

class DaoFactory {
    fun userDao(): UserDao {
        val connectionMaker: ConnectionMaker = getConnectionMaker()

        return UserDao(connectionMaker)
    }

    fun getConnectionMaker(): ConnectionMaker {
        return FConnectionMaker();
    }
}