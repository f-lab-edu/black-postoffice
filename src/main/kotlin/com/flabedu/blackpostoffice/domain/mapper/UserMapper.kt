package com.flabedu.blackpostoffice.domain.mapper

import com.flabedu.blackpostoffice.domain.model.User
import org.apache.ibatis.annotations.Mapper

@Mapper
interface UserMapper {

    fun isExistsByEmail(email: String): Boolean

    fun join(user: User)

    fun getUserByEmail(email: String): User?

    fun getPasswordByEmail(email: String): String

    fun getUserRoleByEmail(email: String): String

    fun getProfileImage(email: String): String?

    fun getNickName(email: String): String

    fun updateUserInfo(user: User)

    fun updateNullProfileImage(email: String)
}
