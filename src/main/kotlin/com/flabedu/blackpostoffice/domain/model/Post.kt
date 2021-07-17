package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class Post(

    val postId: Long = 0L,

    val email: String = "",

    val title: String = "",

    val content: String = "",

    val createdAt: LocalDateTime = LocalDateTime.now()
)
