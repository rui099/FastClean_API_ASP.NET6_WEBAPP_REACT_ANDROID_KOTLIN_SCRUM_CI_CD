package com.example.fastclean.Models.Chat

import java.io.Serializable

data class Chat(
    val id : Int,
    val idCliente : Int,
    val cliente : String,
    val imageCliente: String,
    val funcionario : String,
    val imageFuncionario: String,
    val idFuncionario : Int,
    val mensagens : List<Mensagem>
): Serializable
