package com.example.fastclean.ViewModels.Marcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Marcacao.MarcacaoPost
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class MarcarViewModel  (private val repository: MarcacoesRepository) : ViewModel() {
    val retrofitService  = RetrofitService.getInstance()
    val status = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()


    fun postMarcacaoAgora(mrcacao : MarcacaoPost){
        val request = this.repository.postMarcacaoAgora(mrcacao)
        request.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.code() == 200){
                    status.postValue( true)
                }else{
                    status.postValue(false)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                status.postValue(false  )
            }

        })
    }

    fun postMarcacaoDepois(mrcacao : MarcacaoPost){
        val request = this.repository.postMarcacaoDepois(mrcacao)
        request.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.code() == HttpURLConnection.HTTP_OK){
                    status.postValue( true)
                }else{
                    status.postValue(false)
                    errorMessage.postValue("Marcação falhou")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                status.postValue(false)
                errorMessage.postValue(t.message)
            }

        })
    }
}