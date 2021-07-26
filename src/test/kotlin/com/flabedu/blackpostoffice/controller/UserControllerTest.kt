package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.exception.DuplicateRequestException
import com.flabedu.blackpostoffice.model.user.UserInfoUpdate
import com.flabedu.blackpostoffice.model.user.UserLogin
import com.flabedu.blackpostoffice.model.user.UserSignUp
import com.flabedu.blackpostoffice.service.SessionLoginService
import com.flabedu.blackpostoffice.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation.partWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParts
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import kotlin.jvm.Throws

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(UserController::class)
@ActiveProfiles("test")
internal class UserControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc,
) {

    @MockBean
    lateinit var userService: UserService

    @MockBean
    lateinit var sessionLoginService: SessionLoginService

    lateinit var userSignUp: UserSignUp

    lateinit var userInfoUpdate: UserInfoUpdate

    lateinit var userLogin: UserLogin

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {

        userLogin = UserLogin(
            email = "test10@gmail.com",
            password = "1234test@@"
        )

        userSignUp = UserSignUp(
            email = "1234test@gmail.com",
            password = "1234test@@",
            nickName = "형일",
            address = "서울",
            phone = "010-1234-1234",
        )

        userInfoUpdate = UserInfoUpdate(
            profileImagePath = multipartFile()
        )

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    fun `회원가입 성공`() {

        doNothing().`when`(userService)?.saveUser(userSignUp)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userSignUp))
        )

            .andExpect(status().isCreated)
            .andDo(
                document(
                    "users/create/successful", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")),

                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("프로필로 사용할 닉네임"),
                        fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                        fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호")
                    )
                )
            )
    }

    @Test
    fun `중복된 이메일로 회원가입 실패`() {

        doThrow(DuplicateRequestException("이미 존재하는 이메일 입니다.")).`when`(userService).saveUser(userSignUp)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userSignUp))
        )

            .andExpect(status().isConflict)
            .andDo(
                document(
                    "users/create/fail", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")),

                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("nickName").type(JsonFieldType.STRING).description("프로필로 사용할 닉네임"),
                        fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                        fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호")
                    )
                )
            )
    }

    @Test
    fun `회원정보 수정 성공`() {

        val builder: MockMultipartHttpServletRequestBuilder = MockMvcRequestBuilders.multipart("/users/my-info")
        builder.with { request ->
            request.method = "PATCH"
            request
        }

        doNothing().`when`(userService)?.userInfoUpdate(multipartFile())

        mockMvc.perform(
            builder
                .file(userInfoUpdate.profileImagePath as MockMultipartFile)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo { println() }
            .andExpect(status().isOk)
            .andDo(
                document(
                    "users/my-info/update/successful", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(headerWithName(HttpHeaders.CONTENT_TYPE).description("multipartFile")),

                    requestParts(partWithName("profileImage").description("수정할 프로필 사진 이미지"))
                )
            )
    }

    @Test
    fun `프로필 사진 삭제 성공`() {

        doNothing().`when`(userService)?.deleteProfileImage()

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/profile-image")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(
                document(
                    "users/profile-image/delete", getDocumentRequest(), getDocumentResponse(),

                    requestHeaders(headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-type")),

                    requestParts(partWithName("profileImagePath").description("삭제할 프로필 이미지 경로").optional())
                )
            )
    }

    fun getDocumentRequest() =
        preprocessRequest(modifyUris().scheme("http").host("localhost").port(8080), prettyPrint())

    fun getDocumentResponse() = preprocessResponse(prettyPrint())

    private fun multipartFile() =
        MockMultipartFile("profileImage", "profileImage", "image/png", "profileImage".toByteArray())

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userSignUp: UserSignUp): String {
        return objectMapper.writeValueAsString(userSignUp)
    }
}
