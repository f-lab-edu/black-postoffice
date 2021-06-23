package com.flabedu.blackpostoffice.util.interceptor

import com.flabedu.blackpostoffice.exception.user.UserNotLoginException
import com.flabedu.blackpostoffice.util.annotation.LoginCheck
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginCheckInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val handlerMethod: HandlerMethod = handler as HandlerMethod
        val loginCheck = handlerMethod.getMethodAnnotation(LoginCheck::class.java)
        val getLoginUser = request.session.getAttribute(LOGIN_MY_EMAIL)

        loginCheck ?: return true

        getLoginUser ?: throw UserNotLoginException()

        return true
    }
}
