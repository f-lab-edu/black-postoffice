package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

data class Post(

    val postId: Long = 0L,

    val title: String? = null,

    val content: String? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

) {

    lateinit var email: String private set

    constructor(email: String, title: String?, content: String?) : this(title = title, content = content) {
        this.email = email
    }
}
