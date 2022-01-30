package com.example.fastclean.ViewModels.DetalhesDeMarcacao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesViewModel (private val repository: MarcacoesRepository) : ViewModel(){
    val retrofitService  = RetrofitService.getInstance()
    val user = MutableLiveData<Marcacao>()
    val errorMessage = MutableLiveData<String>()


    fun getDetalhes(id : Int) {

        val request = this.repository.getDetalhes(id)
        request.enqueue(object : Callback<Marcacao> {
            override fun onResponse(
                call: Call<Marcacao>, response: Response<Marcacao>) {
                if(response.code() == 200){
                    user.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao encontrar detalhes")
                }
            }

            override fun onFailure(call: Call<Marcacao>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun TerminarMarcacao(id : Int) {
        val request = this.repository.TerminarMarcacao(id)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.code() == 200){
                }else{
                    errorMessage.postValue("Erro ao terminar marcação")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun iniciarMarcacao(id: Int) {
        val request = this.repository.iniciarMarcacao(id)
        request.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            ) {
                if(response.code() == 200){
                }else{
                    errorMessage.postValue("Não foi possivel encontrar marcações")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }
}