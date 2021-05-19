package com.flabedu.blackpostoffice.domain.user.entity

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val email: String,

    val password: String,

    val nickName: String,

    val address: String,

    val phone: String,

    val createdAt: LocalDateTime

)
