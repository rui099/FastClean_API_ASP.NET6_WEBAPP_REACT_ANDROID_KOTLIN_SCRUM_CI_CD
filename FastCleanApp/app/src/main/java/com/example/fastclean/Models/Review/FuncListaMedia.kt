package com.example.fastclean.Models.Review

import java.io.Serializable

data class FuncListaMedia(
    val media : Float,
    val listaReviews: List<FuncReview>
): Serializable
