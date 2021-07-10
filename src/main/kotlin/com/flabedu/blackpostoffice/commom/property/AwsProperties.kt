package com.flabedu.blackpostoffice.commom.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.aws")
class AwsProperties(

    val credentials: CredentialsProperties,

    val s3: S3Properties,

    val region: RegionProperties,

    val base: BaseProperties

) {

    class CredentialsProperties(val accessKey: String = "", val secretKey: String = "")

    class S3Properties(val bucket: String = "")

    class RegionProperties(val static: String = "")

    class BaseProperties(val url: String = "")
}
