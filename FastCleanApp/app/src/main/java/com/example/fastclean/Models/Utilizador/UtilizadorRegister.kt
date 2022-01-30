package com.example.fastclean.Models.Utilizador

import com.example.fastclean.Models.Marcacao.Marcacao

open class UtilizadorRegister(
val id: Int,
val nome: String,
val password: String,
val cargo: String,
val email: String,
val morada: Morada,
val contacto: Int,
val ccfile: String,
val imagem: String,
val cadastro: String,
val listaDeMarcacoes: List<Marcacao>,
val listaDeChats: String,
)
