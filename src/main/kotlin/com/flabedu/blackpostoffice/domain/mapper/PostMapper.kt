package com.flabedu.blackpostoffice.domain.mapper

import com.flabedu.blackpostoffice.domain.model.Post
import org.apache.ibatis.annotations.Mapper

@Mapper
interface PostMapper {

    fun createMyPost(post: Post)

    fun getPosts(email: String, pageNo: Int, pageSize: Int): List<Post>

    fun updateMyPost(post: Post)

    fun deleteMyPost(post: Long)
}
