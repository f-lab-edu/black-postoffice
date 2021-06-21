package com.flabedu.blackpostoffice.util.interceptor

import com.flabedu.blackpostoffice.domain.model.User.Role.ADMIN
import com.flabedu.blackpostoffice.exception.user.AccessRejectedException
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

        loginCheck ?: return true

        notLoginUser(request)

        adminLevel(request, loginCheck)

        return true
    }

    private fun adminLevel(request: HttpServletRequest, loginCheck: LoginCheck) {
        if (loginCheck.authority == ADMIN) {
            val getUserRole = request.session.getAttribute(MY_ROLE)

            if (getUserRole != ADMIN) {
                throw AccessRejectedException()
            }
        }
    }

    private fun notLoginUser(request: HttpServletRequest) {
        val getLoginUser = request.session.getAttribute(LOGIN_MY_EMAIL)
        getLoginUser ?: throw UserNotLoginException()
    }
}
