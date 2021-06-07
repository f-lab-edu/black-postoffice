package com.flabedu.blackpostoffice.domain

import java.lang.IllegalArgumentException

class User () {
    var id: Int = 0
    var name: String = ""
        set(value) {
            if (value.length > 11) {
                throw Exception("INVALIDNAMEVALUE")
            } else {
                field = value
            }
        }
    var email: String = ""
        set(value) {
            val regex = Regex("[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
            if (!value.matches(regex)) {
                throw Exception("INVALID EMAIL VALUE")
            } else {
                field = value
            }
        }
    // 숫자, 문자, 특수문자 모두 포함 (8~15자)
    var password: String = ""
        set(value) {
            val regex = Regex("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$")
            if(!value.matches(regex)) {
                throw Exception("INVALID PASSWORD VALUE")
            } else {
                field = value
            }
        }

    constructor(id: Int, name: String, email: String, password: String): this() {
        this.id = id
        this.name = name
        this.email = email
        this.password = password
    }
}