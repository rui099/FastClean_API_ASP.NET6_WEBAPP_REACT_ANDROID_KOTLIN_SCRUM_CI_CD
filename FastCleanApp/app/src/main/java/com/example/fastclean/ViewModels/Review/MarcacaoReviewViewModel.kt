package com.example.fastclean.ViewModels.Review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class MarcacaoReviewViewModel (private val repository: MarcacoesRepository) : ViewModel(){
    val retrofitService  = RetrofitService.getInstance()
    val errorMessage = MutableLiveData<String>()

    fun putReviewedCliente(id : Int){
        val request = this.repository.putReviewedCliente(id)
        request.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {

                } else {
                    errorMessage.postValue("Erro a mandar review")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun putReviewedFuncionario(id : Int){
        val request = this.repository.putReviewedFuncionario(id)
        request.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {

                } else {
                    errorMessage.postValue("Erro a mandar review")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}