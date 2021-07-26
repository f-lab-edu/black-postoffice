package com.flabedu.blackpostoffice.mapper

import com.flabedu.blackpostoffice.commom.enumeration.Role
import com.flabedu.blackpostoffice.model.user.UserInfo
import com.flabedu.blackpostoffice.model.user.UserSignUp
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface UserMapper {

    @Select(
        """
        SELECT EXISTS
                   (SELECT email FROM user WHERE email = #{email});            
        """
    )
    fun isExistsByEmail(email: String): Boolean

    @Insert(
        """
        INSERT INTO user (address, created_at, email, nick_name, password, phone, user_role, profile_image)
        VALUES (#{userSignUp.address}, now(), #{userSignUp.email}, #{userSignUp.nickName}, #{userSignUp.password}, 
        #{userSignUp.phone}, #{role}, null);            
        """
    )
    fun join(userSignUp: UserSignUp, role: Role)

    @Select(
        """
        SELECT address, email, nick_name, password, phone, profile_image
        FROM user
        WHERE email = #{email}            
        """
    )
    fun getUserByEmail(email: String): UserInfo?

    @Select(
        """
        SELECT password
        FROM user
        WHERE email = #{email}            
        """
    )
    fun getPasswordByEmail(email: String): String

    @Select(
        """
        SELECT user_role
        FROM user
        WHERE email = #{email}            
        """
    )
    fun getUserRoleByEmail(email: String): String

    @Select(
        """
        SELECT profile_image
        FROM user
        WHERE email = #{email}            
        """
    )
    fun getProfileImage(email: String): String?

    @Select(
        """
        SELECT nick_name
        FROM user
        WHERE email = #{email}        
        """
    )
    fun getNickName(email: String): String

    @Update(
        """
        UPDATE user
        SET profile_image = #{profileImagePath}
        WHERE email = #{email}            
        """
    )
    fun updateUserInfo(email: String, profileImagePath: String)

    @Update(
        """
        UPDATE user
        SET profile_image = null
        WHERE email = #{email}            
        """
    )
    fun updateNullProfileImage(email: String)
}
