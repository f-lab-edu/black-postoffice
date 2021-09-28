package com.flabedu.blackpostoffice.commom.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis.cache")
class RedisCacheProperties(
    val host: String,
    val port: Int,
    val key: String,
    val ttl: Long
)
