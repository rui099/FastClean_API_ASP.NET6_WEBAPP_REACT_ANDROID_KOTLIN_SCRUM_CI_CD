package com.example.fastclean.Models.Marcacao

import java.io.Serializable

data class MarcacaoPost(
    val tipoImovel: String,
    val tipoLimpeza: String,
    val tipoAgendamento: String,
    val numQuartos: String,
    val detalhes: String,
    val diaHora: String,
    val numCasasDeBanho: String,
    val cozinha: Boolean,
    val sala: Boolean,
    val cliente: Int,
    val funcionario: String?,
    val MoradaMarcacao: String,
    val LatitudeMarcacao: String?,
    val LongitudeMarcacao: String?,
    val numHorasPrevistas: String
): Serializable
