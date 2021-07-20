package com.flabedu.blackpostoffice.controller.dto.post

import com.flabedu.blackpostoffice.domain.model.Post
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class PostDto(

    @field:NotBlank(message = "제목을 입력해주세요.")
    @field:Size(max = 20, message = "게시물 제목은 20자 이하로 입력해주세요.")
    val title: String,

    @field:NotBlank(message = "내용을 입력해세요.")
    val content: String,
) {

    fun toCreatePostEntity(email: String) =
        Post.toCreatePostEntity(
            email = email,
            title = title,
            content = content
        )

    fun toUpdatePostEntity(postId: Long) =
        Post.toUpdatePostEntity(
            postId = postId,
            title = title,
            content = content
        )
}
