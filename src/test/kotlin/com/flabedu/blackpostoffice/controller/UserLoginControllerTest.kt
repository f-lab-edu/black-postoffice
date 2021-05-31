package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.exception.user.EmailNotExistsException
import com.flabedu.blackpostoffice.exception.user.WrongPasswordException
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebMvcTest(UserLoginController::class)
internal class UserLoginControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc
) {

    @MockBean
    lateinit var sessionLoginService: SessionLoginService

    @Test
    @DisplayName("로그인 성공")
    fun successLogin() {
        val userLoginDto = UserLoginDto(
            email = "1234test@gmail.com",
            password = "1234test@@",
        )

        doNothing().`when`(sessionLoginService).login(userLoginDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(userLoginDto))

        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("가입되지 않은 이메일로 인하여 로그인 실패")
    fun emailNotExistsLogin() {
        val userLoginDto = UserLoginDto(
            email = "1234test@gmail.com",
            password = "1234test@@",
        )

        Mockito.doThrow(EmailNotExistsException::class.java).`when`(sessionLoginService).login(userLoginDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(userLoginDto))

        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("일치하지 않은 비밀번호로 인하여 로그인 실패")
    fun unauthorizedPasswordLogin() {
        val userLoginDto = UserLoginDto(
            email = "1234test@gmail.com",
            password = "1234test@@",
        )

        Mockito.doThrow(WrongPasswordException::class.java).`when`(sessionLoginService).login(userLoginDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(userLoginDto))

        )
            .andExpect(MockMvcResultMatchers.status().isUnauthorized)
            .andDo(MockMvcResultHandlers.print())
    }

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build()
    }

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userLoginDto: UserLoginDto): String {
        return objectMapper.writeValueAsString(userLoginDto)
    }
}
