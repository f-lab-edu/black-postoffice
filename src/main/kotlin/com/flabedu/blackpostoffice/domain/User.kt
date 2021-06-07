package com.flabedu.blackpostoffice.domain

import java.lang.IllegalArgumentException

class User() {
    var id: Int = 0

    var name: String = ""
        set(value) {
            if (value.length < 2 || value.length > 13) {
                throw IllegalArgumentException("INVALID NAME VALUE")
            } else {
                try {
                    field = value
                } catch (e: Exception) {
                    // TODO: 회원 이름 등록 시 예상하지 못한 예외 처리
                    throw Exception()
                } finally {
                    field = ""
                }
            }
        }

    var email: String = ""
        set(value) {
            val regex = Regex("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")

            if (!value.matches(regex)) {
                throw IllegalArgumentException("INVALID EMAIL VALUE")
            } else {
                try {
                    field = value
                } catch (e: Exception) {
                    // TODO: 회원 이메일 등록 시 예상하지 못한 예외 처리
                    throw Exception()
                } finally {
                    field = ""
                }
            }
        }

    // 숫자, 문자, 특수문자 모두 포함 (8~15자)
    var password: String = ""
        set(value) {
            val regex = Regex("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$")

            if (!value.matches(regex)) {
                throw IllegalArgumentException("INVALID PASSWORD VALUE")
            } else {
                try {
                    field = value
                } catch (e: Exception) {
                    throw Exception()
                } finally {
                    field = ""
                }
            }
        }

    constructor(id: Int, name: String, email: String, password: String) : this() {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
    }
}