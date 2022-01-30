package com.example.fastclean.Models.Utilizador

data class Morada(
    val rua : String,
    val numero : Int,
    val codigoPostal : String,
    val freguesia : String,
    val concelho: String,
    val distrito: String
)
