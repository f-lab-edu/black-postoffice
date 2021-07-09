package com.flabedu.blackpostoffice.controller

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.flabedu.blackpostoffice.controller.dto.UserInfoUpdateDto
import com.flabedu.blackpostoffice.controller.dto.UserLoginDto
import com.flabedu.blackpostoffice.controller.dto.UserSignUpDto
import com.flabedu.blackpostoffice.exception.DuplicateRequestException
import com.flabedu.blackpostoffice.service.SessionLoginService
import com.flabedu.blackpostoffice.service.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@WebMvcTest(UserController::class)
internal class UserControllerTest @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val webApplicationContext: WebApplicationContext,
    private var mockMvc: MockMvc
) {

    @MockBean
    lateinit var userService: UserService

    @MockBean
    lateinit var sessionLoginService: SessionLoginService

    lateinit var userSignUpDto: UserSignUpDto

    lateinit var userInfoUpdateDto: UserInfoUpdateDto

    lateinit var userLoginDto: UserLoginDto

    @BeforeEach
    fun setUp() {

        userLoginDto = UserLoginDto(
            email = "test10@gmail.com",
            password = "1234test@@"
        )

        userSignUpDto = UserSignUpDto(
            email = "1234test@gmail.com",
            password = "1234test@@",
            nickName = "형일",
            address = "서울",
            phone = "010-1234-1234",
            profileImagePath = ""
        )

        userInfoUpdateDto = UserInfoUpdateDto(
            profileImagePath = multipartFile()
        )

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    fun `회원가입 성공`() {

        doNothing().`when`(userService)?.saveUser(userSignUpDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userSignUpDto))
        )

            .andExpect(status().isCreated)
    }

    @Test
    fun `중복된 이메일로 회원가입 실패`() {

        doThrow(DuplicateRequestException("이미 존재하는 이메일 입니다.")).`when`(userService).saveUser(userSignUpDto)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(userSignUpDto))
        )

            .andExpect(status().isConflict)
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
                .file(userInfoUpdateDto.profileImagePath as MockMultipartFile)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo { println() }
            .andExpect(status().isOk)
    }

    @Test
    fun `프로필 사진 삭제 성공`() {

        doNothing().`when`(userService)?.deleteProfileImage()

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/profile-image")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    private fun multipartFile() =
        MockMultipartFile("profileImage", "profileImage", "image/png", "profileImage".toByteArray())

    @Throws(JsonProcessingException::class)
    private fun toJsonString(userSignUpDto: UserSignUpDto): String {
        return objectMapper.writeValueAsString(userSignUpDto)
    }
}
