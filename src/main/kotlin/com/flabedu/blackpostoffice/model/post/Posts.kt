package com.flabedu.blackpostoffice.model.post

data class Posts(
    val nickName: String,
    val profileImagePath: String?,
    val posts: List<Post>
)
