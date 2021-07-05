package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.commom.encryption.Sha256Encryption
import com.flabedu.blackpostoffice.commom.utils.constants.JPEG
import com.flabedu.blackpostoffice.commom.utils.constants.PNG
import com.flabedu.blackpostoffice.controller.dto.UserInfoUpdateDto
import com.flabedu.blackpostoffice.controller.dto.UserSignUpDto
import com.flabedu.blackpostoffice.domain.mapper.UserMapper
import com.flabedu.blackpostoffice.exception.AmazonServiceException
import com.flabedu.blackpostoffice.exception.DuplicateRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption,
    private val amazonS3Service: AmazonS3Service,
    private val sessionLoginService: SessionLoginService
) {

    @Transactional
    fun saveUser(userSignUpDto: UserSignUpDto) {

        duplicateEmailCheck(userSignUpDto.email)

        userMapper.join(userSignUpDto.toUserEntity(sha256Encryption.encryption(userSignUpDto.password)))
    }

    @Transactional
    fun userInfoUpdate(multipartFile: MultipartFile) = profileImageUpdate(multipartFile)


    @Transactional
    fun deleteProfileImage() {

        val getCurrentUserEmail = sessionLoginService.getCurrentUserEmail()
        val getMyProfileImage = userMapper.getProfileImage(getCurrentUserEmail)

        if (getMyProfileImage != null) {
            userMapper.updateNullProfileImage(getCurrentUserEmail)
            amazonS3Service.deleteProfileImage(getMyProfileImage)
        }
    }

    private fun profileImageUpdate(multipartFile: MultipartFile) {

        imageTypeCheck(multipartFile)

        val getCurrentUserEmail = sessionLoginService.getCurrentUserEmail()
        val userInfoUpdateDto = UserInfoUpdateDto(multipartFile)
        val uploadProfileImage = amazonS3Service.updateProfileImage(userInfoUpdateDto.profileImagePath)

        deleteProfileImage()
        userMapper.updateUserInfo(userInfoUpdateDto.toUserInfoUpdate(getCurrentUserEmail, uploadProfileImage))
    }

    private fun imageTypeCheck(multipartFile: MultipartFile) {

        val imageType = multipartFile.contentType

        if (imageType != PNG && imageType != JPEG) {
            throw AmazonServiceException("JPEG 파일이나 PNG 파일만 업로드 하실 수 있습니다.")
        }
    }

    private fun duplicateEmailCheck(email: String) {

        if (userMapper.isExistsByEmail(email)) {
            throw DuplicateRequestException("이미 존재하는 이메일 입니다.")
        }
    }
}
