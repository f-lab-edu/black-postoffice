package com.flabedu.blackpostoffice.service

import org.springframework.web.multipart.MultipartFile

interface FileUploadService {

    fun updateProfileImage(file: MultipartFile): String

    fun deleteProfileImage(getMyProfileImage: String?)
}
