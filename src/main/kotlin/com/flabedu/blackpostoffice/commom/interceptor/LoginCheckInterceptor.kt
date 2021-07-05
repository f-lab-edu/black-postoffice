package com.flabedu.blackpostoffice.commom.interceptor

import com.flabedu.blackpostoffice.commom.annotation.LoginCheck
import com.flabedu.blackpostoffice.domain.model.User.Role.ADMIN
import com.flabedu.blackpostoffice.exception.UnauthorizedAccessException
import com.flabedu.blackpostoffice.service.SessionLoginService
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginCheckInterceptor(

    private val sessionLoginService: SessionLoginService

) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val handlerMethod: HandlerMethod = handler as HandlerMethod
        val loginCheck = handlerMethod.getMethodAnnotation(LoginCheck::class.java)

        loginCheck ?: return true

        validateNotLoginUser()

        validateAccessLevel(loginCheck)

        return true
    }

    private fun validateAccessLevel(loginCheck: LoginCheck) {
        if (loginCheck.authority == ADMIN && sessionLoginService.getCurrentUserRole() != ADMIN) {
            throw UnauthorizedAccessException("이 페이지에 엑세스 하는데 필요한 권한이 존재하지 않습니다.")
        }
    }

    private fun validateNotLoginUser() = sessionLoginService.getCurrentUserEmail()
}
