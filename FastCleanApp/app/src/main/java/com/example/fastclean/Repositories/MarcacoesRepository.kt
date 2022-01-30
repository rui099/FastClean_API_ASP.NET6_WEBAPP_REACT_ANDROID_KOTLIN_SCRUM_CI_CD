package com.example.fastclean.Repositories

import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Marcacao.MarcacaoPost
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

class MarcacoesRepository constructor(private val retrofitService : RetrofitService) {

    fun postMarcacaoAgora(marcacao : MarcacaoPost) = retrofitService.postMarcacaoAgora(marcacao)

    fun postMarcacaoDepois(marcacao : MarcacaoPost) = retrofitService.postMarcacaoDepois(marcacao)

    fun getMarcacoesDecorrer(id : Int) = retrofitService.getMarcacoesDecorrer(id)

    fun getMarcacoesTerminadas(id : Int) = retrofitService.getMarcacoesTerminadas(id)

    fun FuncAceita(id : Int) = retrofitService.updateFuncAceita(id)

    fun TerminarMarcacao(id : Int) = retrofitService.updateTerminarMarcacao(id)

    fun getDetalhes(id: Int) = retrofitService.getDetalhes(id)

    fun putReviewedCliente(id : Int) = retrofitService.putReviewedCliente(id)

    fun putReviewedFuncionario(id : Int) = retrofitService.putReviewedFuncionario(id)

    fun getPedidos(id : Int) = retrofitService.getPedidos(id)

    fun putAceitarPedido(id:Int,idFuncionario:Int) = retrofitService.putAceitarPedido(id,idFuncionario)

    fun rejeitarPedido(id:Int,idFuncionario:Int) = retrofitService.RecusarPedido(id,idFuncionario)

    fun iniciarMarcacao(id: Int) = retrofitService.iniciarMarcacao(id)
}