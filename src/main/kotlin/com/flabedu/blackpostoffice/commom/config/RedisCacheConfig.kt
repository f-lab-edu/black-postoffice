package com.flabedu.blackpostoffice.commom.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.flabedu.blackpostoffice.commom.property.RedisCacheProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableCaching
class RedisCacheConfig(val cacheProperties: RedisCacheProperties) {

    @Bean("redisCacheConnectionFactory")
    fun redisCacheConnectionFactory() = LettuceConnectionFactory(cacheProperties.host, cacheProperties.port)

    @Bean
    fun redisCacheManager() =
        RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisCacheConnectionFactory())
            .cacheDefaults(redisCacheDefaultConfiguration()).build()

    @Bean
    fun objectMapper() = ObjectMapper().apply {

        val ptv = BasicPolymorphicTypeValidator.builder().allowIfSubType(Any::class.java).build()

        registerModule(ParameterNamesModule())
        activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
    }

    private fun redisCacheDefaultConfiguration() = RedisCacheConfiguration.defaultCacheConfig()
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(
                GenericJackson2JsonRedisSerializer(objectMapper())
            )
        ).entryTtl(Duration.ofSeconds(cacheProperties.ttl))
}
