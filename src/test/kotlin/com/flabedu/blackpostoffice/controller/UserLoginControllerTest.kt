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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
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
    fun `????????? ??????`() {

        session.setAttribute("email", userLogin.email)

        doNothing().`when`(sessionLoginService).login(userLogin)

        mockMvc.perform(
            post("/users/login")
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
                        fieldWithPath("email").type(JsonFieldType.STRING).description("???????????? ?????? ?????????"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("???????????? ?????? ????????????"),
                    )
                )
            )
    }

    @Test
    fun `???????????? ?????? ???????????? ?????? ???????????? ?????? ??????????????? ?????? ????????? ??????`() {

        Mockito.doThrow(InvalidRequestException("???????????? ???????????? ????????? ??????????????? ???????????? ????????????.")).`when`(sessionLoginService)
            .login(userLogin)

        mockMvc.perform(
            post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userLogin))
        )

            .andExpect(status().isUnauthorized)
            .andDo(
                document(
                    "users/login/fail", getDocumentRequest(), getDocumentResponse(),

                    requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("???????????? ?????? ?????????"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("???????????? ?????? ????????????"),
                    )
                )
            )
    }

    @Test
    fun `??????????????? ????????? ????????? ????????? ??????`() {

        doNothing().`when`(sessionLoginService).logout()

        session.setAttribute("email", userLogin.email)

        mockMvc.perform(
            post("/users/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )

            .andDo { session.invalidate() }
            .andExpect(request().sessionAttribute("email", nullValue()))
            .andExpect(status().isOk)
            .andDo(document("users/logout/successful"))
    }

    fun getDocumentRequest() =
        preprocessRequest(modifyUris().scheme("http").host("localhost").port(8080), prettyPrint())

    fun getDocumentResponse() = preprocessResponse(prettyPrint())

    @Throws(JsonProcessingException::class)
    private fun toJsonString(dto: Any): String {
        return objectMapper.writeValueAsString(dto)
    }
}
