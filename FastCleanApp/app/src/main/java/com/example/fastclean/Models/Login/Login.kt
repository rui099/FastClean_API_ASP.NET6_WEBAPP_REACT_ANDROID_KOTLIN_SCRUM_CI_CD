package com.example.fastclean.Models.Login

import java.io.Serializable

data class Login(
    val email: String,
    val password: String
): Serializable
