package com.example.fastclean.Models.Review

import com.example.fastclean.Models.Utilizador.Cliente.ClienteRegister
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioRegister
import java.io.Serializable

data class ClienteReview(
    val id : Int,
    val comentario : String,
    val cotacao : Float,
    val reviewedId: Int,
    val reviewedName: String,
    val reviewerId: Int,
    val reviewerName: String,
): Serializable
