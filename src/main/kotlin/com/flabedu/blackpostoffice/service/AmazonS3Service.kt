package com.flabedu.blackpostoffice.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.DeleteObjectRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.flabedu.blackpostoffice.commom.property.AwsProperties
import com.flabedu.blackpostoffice.commom.utils.constants.PROFILE_IMAGE_DIR
import com.flabedu.blackpostoffice.exception.FileRequestException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.lang.StringBuilder
import java.util.UUID

@Service
class AmazonS3Service(

    private val awsProperties: AwsProperties,
    private val amazonS3Client: AmazonS3Client

) : FileUploadService {

    override fun updateProfileImage(file: MultipartFile) = upload(file)

    override fun deleteProfileImage(getMyProfileImage: String?) {
        getMyProfileImage?.let { delete(it) }
    }

    private fun upload(file: MultipartFile): String {

        val fileName: String? = file.originalFilename
        val noExtensionFileName = fileName?.let { fileName.run { substring(it.lastIndexOf('.')) } }
        val convertFileName = StringBuilder().append(getUuid()).append(noExtensionFileName).toString()

        putS3(file, convertFileName)

        return amazonS3Client.getUrl(awsProperties.s3?.bucket, convertFileName).toString()
    }

    private fun putS3(file: MultipartFile, convertFileName: String) {

        val key = StringBuilder().append(PROFILE_IMAGE_DIR).append(convertFileName).toString()

        try {
            amazonS3Client.putObject(PutObjectRequest(awsProperties.s3?.bucket, key, file.inputStream, metadata(file)))
        } catch (e: IOException) {
            throw FileRequestException("파일 업로드에 실패 하였습니다.", e)
        }
    }

    private fun metadata(file: MultipartFile) = ObjectMetadata().apply {

        contentType = file.contentType
        contentLength = file.size
    }

    private fun getUuid() = UUID.randomUUID().toString().replace("-", "")

    private fun delete(getMyProfileImage: String) {

        val key =
            StringBuilder().append(PROFILE_IMAGE_DIR)
                .append(awsProperties.base?.url?.let { getMyProfileImage.substring(it.length) }).toString()

        try {
            amazonS3Client.deleteObject(DeleteObjectRequest(awsProperties.s3?.bucket, key))
        } catch (e: IOException) {
            throw FileRequestException("파일 삭제에 실패하였습니다.", e)
        }
    }
}
