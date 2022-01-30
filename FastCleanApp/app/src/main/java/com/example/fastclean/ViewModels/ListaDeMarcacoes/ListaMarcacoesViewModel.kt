package com.example.fastclean.ViewModels.ListaDeMarcacoes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaMarcacoesViewModel (private val repository: MarcacoesRepository) : ViewModel() {
    val retrofitService  = RetrofitService.getInstance()
    val marcacaoList = MutableLiveData<List<Marcacao>>()
    val errorMessage = MutableLiveData<String>()


    fun marcacoesADecorrer(id : Int) {

        val request = this.repository.getMarcacoesDecorrer(id)
        request.enqueue(object : Callback<List<Marcacao>>{
            override fun onResponse(
                call: Call<List<Marcacao>>, response: Response<List<Marcacao>>
            ) {
                if(response.code() == 200){
                    marcacaoList.postValue( response.body())
                }else{
                    errorMessage.postValue("Não foi possivel encontrar marcações")
                }
            }

            override fun onFailure(call: Call<List<Marcacao>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun marcacoesTerminadas(id : Int) {

        val request = this.repository.getMarcacoesTerminadas(id)
        request.enqueue(object : Callback<List<Marcacao>>{
            override fun onResponse(
                call: Call<List<Marcacao>>, response: Response<List<Marcacao>>
            ) {
                if(response.code() == 200){
                    marcacaoList.postValue( response.body())
                }else{
                    errorMessage.postValue("Não foi possivel encontrar marcações")
                }
            }

            override fun onFailure(call: Call<List<Marcacao>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

}