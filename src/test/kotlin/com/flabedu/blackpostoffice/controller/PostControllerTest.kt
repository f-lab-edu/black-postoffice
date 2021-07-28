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
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
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
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
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
            .andDo(
                document(
                    "posts/create/successful", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("Content-type")
                    ),

                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                    )
                )
            )
    }

    @Test
    fun `게시물 수정에 성공할 경우 Http Status Code 200(Ok) 반환`() {

        doNothing().`when`(postService)?.updateMyPost(1, post)

        mockMvc.perform(
            patch("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(post))
        )

            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                document(
                    "posts/update/successful", getDocumentRequest(), getDocumentResponse(),

                    pathParameters(parameterWithName("id").description("수정할 게시글의 번호")),

                    requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("Content-type")
                    ),

                    requestFields(
                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목 수정"),
                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용 수정"),
                    )
                )
            )
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
            .andDo(
                document(
                    "posts/get/successful", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(
                        HeaderDocumentation.headerWithName(HttpHeaders.CONTENT_TYPE)
                            .description("Content-type")
                    ),

                    requestParameters(
                        parameterWithName("email").description("조회할 게시글을 작성한 사용자 이메일").optional(),
                        parameterWithName("pageNo").description("조회할 게시글의 페이지").optional(),
                        parameterWithName("pageSize").description("조회할 게시글의 페이지당 게시글 수").optional()
                    ),

                    responseFields(
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("조회한 게시글을 작성한 사용자의 닉네임"),
                        fieldWithPath("profileImagePath").type(JsonFieldType.NULL).description("조회한 게시글을 작성한 사용자의 프로필 사진"),
                        fieldWithPath("posts.[].title").type(JsonFieldType.STRING).description("조회한 게시물의 제목"),
                        fieldWithPath("posts.[].content").type(JsonFieldType.STRING).description("조회한 게시물의 내용.")
                    )
                )
            )
    }

    @Test
    fun `게시물 삭제 성공시 Http Status Code 200(Ok) 반환`() {

        doNothing().`when`(postService)?.deleteMyPost(1)

        mockMvc.perform(
            delete("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(
                document(
                    "posts/delete/successful",

                    pathParameters(
                        parameterWithName("id").description("삭제할 게시물의 번호")
                    )
                )
            )
    }

    fun getDocumentRequest() =
        Preprocessors.preprocessRequest(
            Preprocessors.modifyUris().scheme("http").host("localhost").port(8080),
            Preprocessors.prettyPrint()
        )

    fun getDocumentResponse() = Preprocessors.preprocessResponse(Preprocessors.prettyPrint())

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userSignUpDto: Any): String {
        return objectMapper.writeValueAsString(userSignUpDto)
    }
}
