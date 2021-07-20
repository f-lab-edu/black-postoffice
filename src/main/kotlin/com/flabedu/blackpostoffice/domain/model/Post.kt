package com.flabedu.blackpostoffice.domain.model

import java.time.LocalDateTime

class Post() {

    var postId: Long = 0L; private set

    lateinit var email: String private set

    lateinit var title: String private set

    lateinit var content: String private set

    lateinit var createdAt: LocalDateTime private set

    constructor(email: String, title: String, content: String) : this() {
        this.email = email
        this.title = title
        this.content = content
        this.createdAt = LocalDateTime.now()
    }

    constructor(postId: Long, title: String, content: String) : this() {
        this.postId = postId
        this.title = title
        this.content = content
    }

    companion object {

        fun toCreatePostEntity(email: String, title: String, content: String) = Post(email, title, content)

        fun toUpdatePostEntity(postId: Long, title: String, content: String) = Post(postId, title, content)
    }
}
