package com.example.fastclean.Models.Utilizador

import java.io.Serializable

data class PasswordDTO (
    val oldPassword : String,
    val password: String,
    val confirm_Password: String
        ): Serializable