package com.flabedu.blackpostoffice.commom.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.flabedu.blackpostoffice.commom.property.AwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonS3Config(

    private val awsProperties: AwsProperties

) {

    @Bean
    fun amazonS3Client(): AmazonS3Client {

        val basicAWSCredentials = BasicAWSCredentials(awsProperties.credentials.accessKey, awsProperties.credentials.secretKey)

        return AmazonS3ClientBuilder.standard()
            .withRegion(awsProperties.region.static)
            .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials))
            .build() as AmazonS3Client
    }
}
