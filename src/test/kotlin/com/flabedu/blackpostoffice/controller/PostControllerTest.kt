package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.controller.dto.post.PostDto
import com.flabedu.blackpostoffice.controller.dto.post.PostsDto
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

    private lateinit var postDto: PostDto
    private var postList: MutableList<PostDto> = arrayListOf()
    private lateinit var postsDto: PostsDto

    @BeforeEach
    fun setUp() {

        postDto = PostDto(
            title = "제목",
            content = "내용"
        )

        postList.add(postDto)

        postsDto = PostsDto(
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

        doNothing().`when`(postService)?.createMyPost(postDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(postDto))
        )

            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `게시물 수정에 성공할 경우 Http Status Code 200(Ok) 반환`() {

        doNothing().`when`(postService)?.updateMyPost(1, postDto)

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(postDto))
        )

            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `게시물 조회에 성공할 경우 Http Status Code 200(Ok) 반환`() {

        given(postService.getPosts("test@gmail.com", 0, 10)).willReturn(postsDto)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/posts?email=test@gmail.com&pageNo=0&pageSize=10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(postsDto))
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
