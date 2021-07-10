package com.flabedu.blackpostoffice.commom.interceptor

import com.flabedu.blackpostoffice.commom.annotation.AuthorizedAccessCheck
import com.flabedu.blackpostoffice.exception.UnauthorizedAccessException
import com.flabedu.blackpostoffice.service.LoginService
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class LoginCheckInterceptor(

    private val sessionLoginService: LoginService,
    private val environment: Environment

) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val activeProfiles: Array<String> = environment.activeProfiles
        if (activeProfiles[0] == "test") {
            return true
        }

        val handlerMethod: HandlerMethod = handler as HandlerMethod
        val authorizedAccessCheck = handlerMethod.getMethodAnnotation(AuthorizedAccessCheck::class.java)

        authorizedAccessCheck ?: return true

        validateNotLoginUser()

        validateAccessLevel(authorizedAccessCheck)

        return true
    }

    private fun validateAccessLevel(authorizedAccessCheck: AuthorizedAccessCheck) {

        if (authorizedAccessCheck.authority != sessionLoginService.getCurrentUserRole()) {
            throw UnauthorizedAccessException("이 페이지에 엑세스 하는데 필요한 권한이 존재하지 않습니다.")
        }
    }

    private fun validateNotLoginUser() = sessionLoginService.getCurrentUserEmail()
}
