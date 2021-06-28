package com.flabedu.blackpostoffice.commom.interceptor

import com.flabedu.blackpostoffice.commom.annotation.LoginCheck
import com.flabedu.blackpostoffice.commom.utils.constants.LOGIN_MY_EMAIL
import com.flabedu.blackpostoffice.commom.utils.constants.MY_ROLE
import com.flabedu.blackpostoffice.domain.model.User.Role.ADMIN
import com.flabedu.blackpostoffice.exception.UnauthorizedAccessException
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

        validateNotLoginUser(request)

        validateAdminLevel(request, loginCheck)

        return true
    }

    private fun validateAdminLevel(request: HttpServletRequest, loginCheck: LoginCheck) {
        if (loginCheck.authority == ADMIN) {
            request.session.getAttribute(MY_ROLE)
                ?: throw UnauthorizedAccessException("이 페이지에 엑세스 하는데 필요한 권한이 존재하지 않습니다.")
        }
    }

    private fun validateNotLoginUser(request: HttpServletRequest) {
        request.session.getAttribute(LOGIN_MY_EMAIL) ?: throw UnauthorizedAccessException("로그인 후에 이용 가능합니다.")
    }

}
