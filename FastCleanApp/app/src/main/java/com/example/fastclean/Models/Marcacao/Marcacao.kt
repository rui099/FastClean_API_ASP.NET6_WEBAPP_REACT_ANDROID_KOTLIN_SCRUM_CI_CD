package com.example.fastclean.Models.Marcacao


import java.io.Serializable

data class Marcacao(
    val marcacaoId : Int,
    val tipoImovel : String,
    val tipoLimpeza : String,
    val tipoAgendamento : String,
    val numQuartos : Int,
    val numCasasDeBanho : Int,
    val cozinha : Boolean,
    val sala : Boolean,
    val detalhes : String,
    val diaHora : String,
    val horaInicio: String,
    val horaFinal : String,
    val clienteID : Int,
    val cliente : String,
    val funcionarioID : Int,
    val funcionario: String,
    val marcacaoAceitePeloFunc : Boolean,
    val marcacaoAceitePeloCliente : Boolean,
    val terminada : Boolean,
    val numHorasPrevistas : Int,
    val moradaMarcacao : String,
    val latitudeMarcacao :String,
    val longitudeMarcacao : String,
    var reviewCliente : Boolean,
    var reviewFuncionario : Boolean,
    var total: Float,
    var estadoFuncionario: String
): Serializable
