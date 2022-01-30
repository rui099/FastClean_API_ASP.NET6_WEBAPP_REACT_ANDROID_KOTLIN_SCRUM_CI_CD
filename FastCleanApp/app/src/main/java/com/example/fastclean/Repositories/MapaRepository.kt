package com.example.fastclean.Repositories

import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioDetails
import com.example.fastclean.RestService.RetrofitService
import retrofit2.Response
import retrofit2.http.Path

class MapaRepository  constructor(private val retrofitService : RetrofitService) {

    suspend fun getFuncRangeProfissional(latitude : Double, longitude : Double, range : Int) : Response<List<FuncionarioDetails>> {
        return retrofitService.getFuncRangeProfissional(latitude, longitude, range)
    }

    suspend fun getFuncRangePNormal(latitude : Double, longitude : Double, range : Int ): Response<List<FuncionarioDetails>> {
        return retrofitService.getFuncRangePNormal(latitude, longitude, range)
    }
}