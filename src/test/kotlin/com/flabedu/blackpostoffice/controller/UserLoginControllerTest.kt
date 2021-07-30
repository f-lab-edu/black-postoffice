package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.exception.InvalidRequestException
import com.flabedu.blackpostoffice.model.user.UserLogin
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(RestDocumentationExtension::class)
@WebMvcTest(UserLoginController::class)
@ActiveProfiles("test")
internal class UserLoginControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc,
) {

    @MockBean
    lateinit var sessionLoginService: SessionLoginService

    lateinit var session: MockHttpSession

    lateinit var userLogin: UserLogin

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {

        session = MockHttpSession()

        userLogin = UserLogin(
            email = "test10@gmail.com",
            password = "1234test@@"
        )

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    fun `로그인 성공`() {

        session.setAttribute("email", userLogin.email)

        doNothing().`when`(sessionLoginService).login(userLogin)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userLogin))
                .session(session)
        )

            .andExpect(request().sessionAttribute("email", notNullValue()))
            .andExpect(status().isOk)
            .andDo(
                document(
                    "users/login/successful", getDocumentRequest(), getDocumentResponse(),
                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("로그인을 위한 이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("로그인을 위한 비밀번호"),
                    )
                )
            )
    }

    @Test
    fun `가입되지 않은 이메일로 또는 일치하지 않는 비밀번호로 인해 로그인 실패`() {

        Mockito.doThrow(InvalidRequestException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")).`when`(sessionLoginService)
            .login(userLogin)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userLogin))
        )

            .andExpect(status().isUnauthorized)
            .andDo(
                document(
                    "users/login/fail", getDocumentRequest(), getDocumentResponse(),

                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("로그인을 위한 이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("로그인을 위한 비밀번호"),
                    )
                )
            )
    }

    @Test
    fun `로그아웃과 동시에 세션을 완전히 삭제`() {

        doNothing().`when`(sessionLoginService).logout()

        session.setAttribute("email", userLogin.email)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )

            .andDo { session.invalidate() }
            .andExpect(request().sessionAttribute("email", nullValue()))
            .andExpect(status().isOk)
            .andDo(document("users/logout/successful"))
    }

    fun getDocumentRequest() =
        Preprocessors.preprocessRequest(
            Preprocessors.modifyUris().scheme("http").host("localhost").port(8080),
            Preprocessors.prettyPrint()
        )

    fun getDocumentResponse() = Preprocessors.preprocessResponse(Preprocessors.prettyPrint())

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userLogin: UserLogin): String {
        return objectMapper.writeValueAsString(userLogin)
    }
}