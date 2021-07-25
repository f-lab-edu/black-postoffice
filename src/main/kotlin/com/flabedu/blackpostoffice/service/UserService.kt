package com.flabedu.blackpostoffice.service

import com.flabedu.blackpostoffice.commom.encryption.Sha256Encryption
import com.flabedu.blackpostoffice.commom.utils.constants.JPEG
import com.flabedu.blackpostoffice.commom.utils.constants.PNG
import com.flabedu.blackpostoffice.exception.DuplicateRequestException
import com.flabedu.blackpostoffice.exception.FileRequestException
import com.flabedu.blackpostoffice.mapper.UserMapper
import com.flabedu.blackpostoffice.model.user.UserInfoUpdate
import com.flabedu.blackpostoffice.model.user.UserSignUp
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UserService(
    private val userMapper: UserMapper,
    private val sha256Encryption: Sha256Encryption,
    private val amazonS3Service: AmazonS3Service,
    private val sessionLoginService: SessionLoginService,
) {

    @Transactional
    fun saveUser(userSignUp: UserSignUp) {

        duplicateEmailCheck(userSignUp.email)

        userMapper.join(
            userSignUp.toPasswordEncryption(sha256Encryption.encryption(userSignUp.password)),
            UserSignUp.Role.USER
        )
    }

    @Transactional
    fun userInfoUpdate(multipartFile: MultipartFile) = profileImageUpdate(multipartFile)

    @Transactional
    fun deleteProfileImage() {

        if (getMyProfileImage(sessionLoginService.getCurrentUserEmail()) != null) {
            userMapper.updateNullProfileImage(sessionLoginService.getCurrentUserEmail())
            amazonS3Service.deleteProfileImage(getMyProfileImage(sessionLoginService.getCurrentUserEmail()))
        }
    }

    fun getMyProfileImage(getCurrentUserEmail: String) = userMapper.getProfileImage(getCurrentUserEmail)

    private fun profileImageUpdate(multipartFile: MultipartFile) {

        imageTypeCheck(multipartFile)

        val uploadProfileImagePath = amazonS3Service.updateProfileImage(UserInfoUpdate(multipartFile).profileImagePath)

        deleteProfileImage()

        userMapper.updateUserInfo(sessionLoginService.getCurrentUserEmail(), uploadProfileImagePath)
    }

    private fun imageTypeCheck(multipartFile: MultipartFile) {

        val imageType = multipartFile.contentType

        if (imageType != PNG && imageType != JPEG) {
            throw FileRequestException("JPEG 파일이나 PNG 파일만 업로드 하실 수 있습니다.")
        }
    }

    private fun duplicateEmailCheck(email: String) {

        if (userMapper.isExistsByEmail(email)) {
            throw DuplicateRequestException("이미 존재하는 이메일 입니다.")
        }
    }
}
