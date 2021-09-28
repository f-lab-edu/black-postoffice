package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.mapper.PostMapper
import com.flabedu.blackpostoffice.mapper.UserMapper
import com.flabedu.blackpostoffice.model.post.Post
import com.flabedu.blackpostoffice.model.post.Posts
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
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
    fun getUserPosts(email: String, pageNo: Int, pageSize: Int) = Posts(
        nickName = userMapper.getNickName(email),
        profileImagePath = userMapper.getProfileImage(email),
        posts = postMapper.getUserPosts(email, pageNo, pageSize).map { Post(title = it.title, content = it.title) }
    )

    @Transactional(readOnly = true)
    @Cacheable(value = ["Posts"])
    fun getUsersPosts(pageNo: Int, pageSize: Int) =
        postMapper.getUsersPosts(pageNo, pageSize).map { Post(title = it.title, content = it.title) }

    @Transactional
    @CacheEvict(value = ["Posts"], allEntries = true)
    fun updateMyPost(postId: Long, updatePost: Post) {
        postMapper.updateMyPost(postId, updatePost)
    }

    @Transactional
    @CacheEvict(value = ["Posts"], allEntries = true)
    fun deleteMyPost(postId: Long) {
        postMapper.deleteMyPost(postId)
    }
}
