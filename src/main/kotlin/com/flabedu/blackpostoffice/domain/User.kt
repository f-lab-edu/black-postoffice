package com.flabedu.blackpostoffice.domain

class User () {
    var id: Int = 0
    var name: String = ""
    var password: String = ""

    constructor(id: Int, name: String, password: String): this() {
        this.id = id
        this.name = name
        this.password = password
    }
}