package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.mapper.PostMapper
import com.flabedu.blackpostoffice.mapper.UserMapper
import com.flabedu.blackpostoffice.model.post.Post
import com.flabedu.blackpostoffice.model.post.Posts
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postMapper: PostMapper,
    private val userMapper: UserMapper,
    private val sessionLoginService: SessionLoginService,
) {

    @Transactional
    fun createMyPost(createPost: Post) {
        postMapper.createMyPost(sessionLoginService.getCurrentUserEmail(), createPost)
    }

    @Transactional(readOnly = true)
    fun getPosts(email: String, pageNo: Int, pageSize: Int) = Posts(
        nickName = userMapper.getNickName(email),
        profileImagePath = userMapper.getProfileImage(email),
        posts = postMapper.getPosts(email, pageNo, pageSize)
            .mapTo(arrayListOf()) { Post(title = it.title, content = it.content) }
    )

    @Transactional
    fun updateMyPost(postId: Long, updatePost: Post) {
        postMapper.updateMyPost(postId, updatePost)
    }

    @Transactional
    fun deleteMyPost(postId: Long) {
        postMapper.deleteMyPost(postId)
    }
}
