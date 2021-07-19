package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class Post(

    val postId: Long? = null,

    val email: String? = null,

    val title: String? = null,

    val content: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now()
)
