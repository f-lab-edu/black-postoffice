package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.model.post.Post
import com.flabedu.blackpostoffice.model.post.Posts
import com.flabedu.blackpostoffice.service.PostService
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@WebMvcTest(PostController::class)
@ActiveProfiles("test")
internal class PostControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc
) {

    @MockBean
    lateinit var postService: PostService

    @MockBean
    lateinit var sessionLoginService: SessionLoginService

    private lateinit var post: Post
    private var postList: MutableList<Post> = arrayListOf()
    private lateinit var posts: Posts

    @BeforeEach
    fun setUp() {

        post = Post(
            title = "제목",
            content = "내용"
        )

        postList.add(post)

        posts = Posts(
            nickName = "닉네임",
            profileImagePath = null,
            posts = postList
        )

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    fun `게시물 작성에 성공할 경우 Http Status Code 201(Created)를 리턴`() {

        doNothing().`when`(postService)?.createMyPost(post)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(post))
        )

            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `게시물 수정에 성공할 경우 Http Status Code 200(Ok) 반환`() {

        doNothing().`when`(postService)?.updateMyPost(1, post)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(post))
        )

            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `게시물 조회에 성공할 경우 Http Status Code 200(Ok) 반환`() {

        given(postService.getPosts("test@gmail.com", 0, 10)).willReturn(posts)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/posts?email=test@gmail.com&pageNo=0&pageSize=10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(posts))
        )

            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `게시물 삭제 성공시 Http Status Code 200(Ok) 반환`() {

        doNothing().`when`(postService)?.deleteMyPost(1)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userSignUpDto: Any): String {
        return objectMapper.writeValueAsString(userSignUpDto)
    }
}
