package com.flabedu.blackpostoffice.util.config

import com.flabedu.blackpostoffice.util.interceptor.LoginCheckInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    private val loginCheckInterceptor: LoginCheckInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loginCheckInterceptor)
    }
}
