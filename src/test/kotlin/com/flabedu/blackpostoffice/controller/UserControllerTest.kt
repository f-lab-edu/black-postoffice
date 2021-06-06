package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.controller.dto.UserDto
import com.flabedu.blackpostoffice.exception.user.DuplicateEmailException
import com.flabedu.blackpostoffice.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime

@WebMvcTest(UserController::class)
internal class UserControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc
) {

    @MockBean
    lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .build()
    }

    @Test
    @DisplayName("회원가입 성공")
    fun createUser() {
        val userDto = UserDto(
            email = "1234test@gmail.com",
            password = "1234test@@",
            nickName = "형일",
            address = "서울",
            phone = "010-1234-1234",
            createdAt = LocalDateTime.now()
        )

        doNothing().`when`(userService)?.saveUser(userDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(userDto))

        )
            .andExpect(status().isCreated)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입 실패")
    fun createUserWithVaildEmail() {
        val userDto = UserDto(
            email = "test123@gmail.com",
            password = "testtest123",
            nickName = "형일",
            address = "서울",
            phone = "010-4321-4321",
            createdAt = LocalDateTime.now()
        )

        doThrow(DuplicateEmailException::class.java).`when`(userService).saveUser(userDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(userDto))
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isConflict)
    }

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userDto: UserDto): String {
        return objectMapper.writeValueAsString(userDto)
    }
}
