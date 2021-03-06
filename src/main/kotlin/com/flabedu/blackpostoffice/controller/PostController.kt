package com.flabedu.blackpostoffice.controller

import com.flabedu.blackpostoffice.commom.annotation.AuthorizedAccessCheck
import com.flabedu.blackpostoffice.commom.enumeration.Role.USER
import com.flabedu.blackpostoffice.model.post.Post
import com.flabedu.blackpostoffice.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class PostController(
    private val postService: PostService,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @AuthorizedAccessCheck(authority = USER)
    fun createPost(@RequestBody createPost: Post) {
        postService.createMyPost(createPost)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @AuthorizedAccessCheck(authority = USER)
    fun getUserPosts(@RequestParam email: String, @RequestParam pageNo: Int, @RequestParam pageSize: Int) =
        postService.getUserPosts(email, pageNo, pageSize)

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @AuthorizedAccessCheck(authority = USER)
    fun getUsersPosts(@RequestParam pageNo: Int, @RequestParam pageSize: Int) =
        postService.getUsersPosts(pageNo, pageSize)

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    @AuthorizedAccessCheck(authority = USER)
    fun updateMyPost(@PathVariable postId: Long, @RequestBody post: Post) {
        postService.updateMyPost(postId, post)
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @AuthorizedAccessCheck(authority = USER)
    fun deleteMyPost(@PathVariable postId: Long) {
        postService.deleteMyPost(postId)
    }
}
