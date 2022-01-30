package com.example.fastclean.Repositories

import com.example.fastclean.Models.Utilizador.Localizacao
import com.example.fastclean.RestService.RetrofitService

class SubRepository constructor(private val retrofitService : RetrofitService) {

    fun getSub(id: Int) = retrofitService.getSub(id)

    fun putNullSub(id: Int) = retrofitService.putNullSub(id)

    fun putSub(id: Int) = retrofitService.putSub(id)

    fun updatelocalizaço(idFunc: Int, localizacao: Localizacao) = retrofitService.updatelocalizaço(idFunc,localizacao)

}