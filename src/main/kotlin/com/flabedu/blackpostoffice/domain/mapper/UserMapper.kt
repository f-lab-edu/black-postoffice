package com.flabedu.blackpostoffice.domain.mapper

import com.flabedu.blackpostoffice.domain.model.User
import org.apache.ibatis.annotations.Mapper


@Mapper
interface UserMapper {

    fun isExistsByEmail(email: String): Boolean
    fun save(user: User)

}