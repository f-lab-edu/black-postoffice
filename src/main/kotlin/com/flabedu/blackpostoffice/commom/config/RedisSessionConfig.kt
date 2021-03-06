package com.flabedu.blackpostoffice.commom.config

import com.flabedu.blackpostoffice.commom.property.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@Configuration
@EnableRedisHttpSession
class RedisSessionConfig(val redisProperties: RedisProperties) {

    @Bean
    @Primary
    fun redisConnectionFactory() = LettuceConnectionFactory(redisProperties.host, redisProperties.port)

    @Bean("sessionRedisTemplate")
    fun sessionRedisTemplate(): RedisTemplate<String, Any> {

        val redisTemplate = RedisTemplate<String, Any>()

        return redisTemplate.apply {
            setConnectionFactory(redisConnectionFactory())
            setEnableTransactionSupport(true)
            keySerializer = StringRedisSerializer()
            valueSerializer = GenericJackson2JsonRedisSerializer()
        }
    }
}
