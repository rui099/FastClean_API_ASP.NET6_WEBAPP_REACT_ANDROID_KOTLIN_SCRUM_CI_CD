package com.example.fastclean.Models.Utilizador.Funcionario

import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.UserImage
import com.example.fastclean.Models.Utilizador.UtilizadorDetails
import java.io.Serializable

class FuncionarioDetails(
    id: Int,
    nome: String,
    val estado: String,
    val mediaReviews : Float,
    val preco : Float,
//    imagem: ByteArray,
    morada: Morada,
    latitude: Double,
    longitude: Double

): UtilizadorDetails(
    id,
    nome,
//    imagem,
    morada,
    latitude,
    longitude

), Serializable