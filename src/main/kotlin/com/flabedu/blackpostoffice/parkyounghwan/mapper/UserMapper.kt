package com.flabedu.blackpostoffice.parkyounghwan.mapper

import com.flabedu.blackpostoffice.parkyounghwan.domain.User
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component("parkyounghwan.UserMapper")
interface UserMapper {
    fun selectUserByEmail(email: String): User
    fun insertUser(user: User): Int
    fun deleteAllUser(): Int
}