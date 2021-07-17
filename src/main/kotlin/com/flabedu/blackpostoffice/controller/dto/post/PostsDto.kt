package com.flabedu.blackpostoffice.controller.dto.post

data class PostsDto(
    val nickName: String,
    val profileImagePath: String?,
    val posts: List<PostDto>
)
