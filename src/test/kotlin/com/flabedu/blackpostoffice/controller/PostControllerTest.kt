package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.model.post.Post
import com.flabedu.blackpostoffice.model.post.Posts
import com.flabedu.blackpostoffice.service.PostService
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(PostController::class)
@ActiveProfiles("test")
internal class PostControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc,
) {

    @MockBean
    lateinit var postService: PostService

    @MockBean
    lateinit var sessionLoginService: SessionLoginService

    private lateinit var post: Post
    private var postList: MutableList<Post> = arrayListOf()
    private lateinit var posts: Posts

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {

        post = Post(
            title = "??????",
            content = "??????"
        )

        postList.add(post)

        posts = Posts(
            nickName = "?????????",
            profileImagePath = null,
            posts = postList
        )

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    fun `????????? ????????? ????????? ?????? Http Status Code 201(Created)??? ??????`() {

        doNothing().`when`(postService)?.createMyPost(post)

        mockMvc.perform(
            post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(post))
        )

            .andExpect(status().isCreated)
            .andDo(
                document(
                    "posts/create/successful", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")
                    ),

                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ??????"),
                    )
                )
            )
    }

    @Test
    fun `????????? ????????? ????????? ?????? Http Status Code 200(Ok) ??????`() {

        doNothing().`when`(postService)?.updateMyPost(1, post)

        mockMvc.perform(
            patch("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(post))
        )

            .andExpect(status().isCreated)
            .andDo(
                document(
                    "posts/update/successful", getDocumentRequest(), getDocumentResponse(),

                    pathParameters(parameterWithName("id").description("????????? ???????????? ??????")),

                    requestHeaders(headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")),

                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                    )
                )
            )
    }

    @Test
    fun `?????? ????????? ????????? ????????? ????????? ?????? Http Status Code 200(Ok) ??????`() {

        given(postService.getUserPosts("test@gmail.com", 0, 10)).willReturn(posts)

        mockMvc.perform(
            get("/posts?email=test@gmail.com&pageNo=0&pageSize=10")
                .contentType(MediaType.APPLICATION_JSON)
        )

            .andExpect(status().isOk)
            .andDo(
                document(
                    "posts/get/successful", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("Content-type")
                    ),

                    requestParameters(
                        parameterWithName("email").description("????????? ???????????? ????????? ????????? ?????????").optional(),
                        parameterWithName("pageNo").description("????????? ???????????? ?????????").optional(),
                        parameterWithName("pageSize").description("????????? ???????????? ???????????? ????????? ???").optional()
                    ),

                    responseFields(
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("????????? ???????????? ????????? ???????????? ?????????"),
                        fieldWithPath("profileImagePath").type(JsonFieldType.NULL)
                            .description("????????? ???????????? ????????? ???????????? ????????? ??????"),
                        fieldWithPath("posts.[].title").type(JsonFieldType.STRING).description("????????? ???????????? ??????"),
                        fieldWithPath("posts.[].content").type(JsonFieldType.STRING).description("????????? ???????????? ??????")
                    )
                )
            )
    }

    @Test
    fun `?????? ????????? ????????? ????????? ????????? ?????? Http Status Code 200(Ok) ??????`() {

        given(postService.getUsersPosts(0, 10)).willReturn(postList)

        mockMvc.perform(
            get("/posts/users?pageNo=0&pageSize=10")
                .contentType(MediaType.APPLICATION_JSON)
        )

            .andExpect(status().isOk)
            .andDo(
                document(
                    "posts/get/users/successful", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(
                        headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("Content-type")
                    ),

                    requestParameters(
                        parameterWithName("pageNo").description("????????? ???????????? ?????????").optional(),
                        parameterWithName("pageSize").description("????????? ???????????? ???????????? ????????? ???").optional()
                    ),

                    responseFields(
                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("????????? ???????????? ??????"),
                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("????????? ???????????? ??????")
                    )
                )
            )
    }

    @Test
    fun `????????? ?????? ????????? Http Status Code 200(Ok) ??????`() {

        doNothing().`when`(postService)?.deleteMyPost(1)

        mockMvc.perform(
            delete("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "posts/delete/successful",

                    pathParameters(
                        parameterWithName("id").description("????????? ???????????? ??????")
                    )
                )
            )
    }

    fun getDocumentRequest() =
        preprocessRequest(modifyUris().scheme("http").host("localhost").port(8080), prettyPrint())

    fun getDocumentResponse() = preprocessResponse(prettyPrint())

    @Throws(JsonProcessingException::class)
    private fun toJsonString(dto: Any): String {
        return objectMapper.writeValueAsString(dto)
    }
}
