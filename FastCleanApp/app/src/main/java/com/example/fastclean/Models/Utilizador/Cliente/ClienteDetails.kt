package com.example.fastclean.Models.Utilizador.Cliente

import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.UtilizadorDetails
import java.io.Serializable

class ClienteDetails(
    id: Int,
    nome: String,
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
