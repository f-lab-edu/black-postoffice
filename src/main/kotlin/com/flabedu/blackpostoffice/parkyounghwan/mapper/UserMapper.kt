package com.flabedu.blackpostoffice.parkyounghwan.mapper

import com.flabedu.blackpostoffice.parkyounghwan.domain.User
import org.apache.ibatis.annotations.Mapper
import org.springframework.beans.factory.annotation.Qualifier

@Mapper
@Qualifier("pUserMapper")
interface UserMapper {
    fun selectUserById(id: Int): User
}