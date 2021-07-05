package com.flabedu.blackpostoffice.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.flabedu.blackpostoffice.commom.utils.constants.PROFILE_IMAGE_DIR
import com.flabedu.blackpostoffice.exception.AmazonServiceException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.UUID

@Service
class AmazonS3Service(

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,

    @Value("\${cloud.aws.base.url}")
    private val baseUrl: String,

    private val amazonS3Client: AmazonS3Client

) {

    fun updateProfileImage(file: MultipartFile) = upload(file)

    fun deleteProfileImage(getMyProfileImage: String?) = getMyProfileImage?.let { delete(it) }

    private fun upload(file: MultipartFile): String {
        val fileName: String? = file.originalFilename
        val noExtensionFileName = fileName?.let { fileName.run { substring(it.lastIndexOf('.')) } }
        val convertFileName = getUuid().plus(noExtensionFileName)

        putS3(file, convertFileName)

        return amazonS3Client.getUrl(bucket, convertFileName).toString()
    }

    private fun putS3(file: MultipartFile, convertFileName: String) {

        try {
            amazonS3Client.putObject(
                PutObjectRequest(bucket, PROFILE_IMAGE_DIR.plus(convertFileName), file.inputStream, metadata(file))
            )
        } catch (e: IOException) {
            throw AmazonServiceException("파일 업로드에 실패 하였습니다.")
        }
    }

    private fun metadata(file: MultipartFile) = ObjectMetadata().apply {

        contentType = file.contentType
        contentLength = file.size
    }

    private fun getUuid() = UUID.randomUUID().toString().replace("-", "")

    private fun delete(getMyProfileImage: String) {

        val key = PROFILE_IMAGE_DIR.plus(getMyProfileImage.substring(baseUrl.length))

        try {
            amazonS3Client.deleteObject(DeleteObjectRequest(bucket, key))
        } catch (e: IOException) {
            throw AmazonServiceException("파일 삭제에 실패하였습니다.")
        }
    }
}
