package com.example.fastclean.Models.Login

import java.io.Serializable

data class UserInfo(val id: Int,
                    val nome: String,
                    val password: String,
                    val cargo: String,
                    val token : String
): Serializable
