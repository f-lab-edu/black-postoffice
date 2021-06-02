package com.flabedu.blackpostoffice.util.interceptor

import com.flabedu.blackpostoffice.exception.user.UserNotLoginException
import com.flabedu.blackpostoffice.util.annotation.LoginCheck
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Component
class LoginCheckInterceptor(
    private var session: HttpSession
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val handlerMethod: HandlerMethod = handler as HandlerMethod
        session = request.session

        if (handlerMethod.getMethodAnnotation(LoginCheck::class.java) == null) {
            return true
        }

        if (session.getAttribute(LOGIN_MY_EMAIL) == null) {
            throw UserNotLoginException()
        }

        return true
    }
}
