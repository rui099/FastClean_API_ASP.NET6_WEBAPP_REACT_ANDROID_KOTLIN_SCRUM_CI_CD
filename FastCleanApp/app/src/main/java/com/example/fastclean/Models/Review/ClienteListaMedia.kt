package com.example.fastclean.Models.Review

import java.io.Serializable

data class ClienteListaMedia(
    val media : Float,
    val listaReviews: List<ClienteReview>
): Serializable