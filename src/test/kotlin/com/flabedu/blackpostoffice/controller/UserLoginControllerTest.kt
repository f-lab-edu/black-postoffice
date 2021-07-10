package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.exception.InvalidRequestException
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpSession
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

@WebMvcTest(UserLoginController::class)
@ActiveProfiles("test")
internal class UserLoginControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc
) {

    @MockBean
    lateinit var sessionLoginService: SessionLoginService

    lateinit var session: MockHttpSession

    lateinit var userLoginDto: UserLoginDto

    @BeforeEach
    fun setUp() {

        session = MockHttpSession()

        userLoginDto = UserLoginDto(
            email = "test10@gmail.com",
            password = "1234test@@"
        )

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    fun `로그인 성공`() {

        session.setAttribute("email", userLoginDto.email)

        doNothing().`when`(sessionLoginService).login(userLoginDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userLoginDto))
                .session(session)
        )

            .andExpect(request().sessionAttribute("email", notNullValue()))
            .andExpect(status().isOk)
    }

    @Test
    fun `가입되지 않은 이메일로 또는 일치하지 않는 비밀번호로 인해 로그인 실패`() {

        Mockito.doThrow(InvalidRequestException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.")).`when`(sessionLoginService).login(userLoginDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userLoginDto))
        )

            .andExpect(status().isUnauthorized)
    }

    @Test
    fun `로그아웃과 동시에 세션을 완전히 삭제`() {

        doNothing().`when`(sessionLoginService).logout()

        session.setAttribute("email", userLoginDto.email)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )

            .andDo { session.invalidate() }
            .andExpect(request().sessionAttribute("email", nullValue()))
            .andExpect(status().isOk)
    }

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userLoginDto: UserLoginDto): String {
        return objectMapper.writeValueAsString(userLoginDto)
    }
}
