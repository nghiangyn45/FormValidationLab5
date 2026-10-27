package com.example.formvalidationlab5.model

import java.io.Serializable

data class User(
    val fullName: String,
    val email: String,
    val phone: String,
    val msv: String,
    val className: String
) : Serializable