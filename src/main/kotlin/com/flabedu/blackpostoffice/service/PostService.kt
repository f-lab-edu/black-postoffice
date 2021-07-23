package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.controller.dto.post.PostDto
import com.flabedu.blackpostoffice.controller.dto.post.PostsDto
import com.flabedu.blackpostoffice.domain.mapper.PostMapper
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postMapper: PostMapper,
    private val userMapper: UserMapper,
    private val sessionLoginService: SessionLoginService,
) {

    @Transactional
    fun createMyPost(createPostDto: PostDto) {
        createPostDto.toCreatePostEntity(sessionLoginService.getCurrentUserEmail()).let { postMapper.createMyPost(it) }
    }

    fun getPosts(email: String, pageNo: Int, pageSize: Int) = PostsDto(
        nickName = userMapper.getNickName(email),
        profileImagePath = userMapper.getProfileImage(email),
        posts = postMapper.getPosts(email, pageNo, pageSize)
            .mapTo(arrayListOf()) { PostDto(title = it.title!!, content = it.content!!) }
    )

    @Transactional
    fun updateMyPost(postId: Long, updatePostDto: PostDto) {
        updatePostDto.toUpdatePostEntity(postId).let { postMapper.updateMyPost(it) }
    }

    @Transactional
    fun deleteMyPost(postId: Long) {
        postMapper.deleteMyPost(postId)
    }
}
