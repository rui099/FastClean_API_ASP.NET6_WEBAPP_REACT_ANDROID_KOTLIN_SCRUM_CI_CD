package com.example.fastclean.Models.Review

import java.io.Serializable

data class Review(
    var comentario : String,
    var cotacao : Float,
    var reviewed: Int,
    var reviewer: Int
): Serializable
