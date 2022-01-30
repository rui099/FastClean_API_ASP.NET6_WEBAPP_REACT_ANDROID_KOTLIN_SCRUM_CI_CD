package com.example.fastclean.ViewModels.Perfil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Review.ClienteListaMedia
import com.example.fastclean.Models.Utilizador.Localizacao
import com.example.fastclean.Repositories.SubRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubViewModel (private val repository: SubRepository) : ViewModel(){
    val retrofitService  = RetrofitService.getInstance()
    val errorMessage = MutableLiveData<String>()
    val sub = MutableLiveData<Boolean>()
    val status = MutableLiveData<Boolean>()

    fun getSub(id : Int) {

        val request = this.repository.getSub(id)
        request.enqueue(object : Callback<Boolean> {
            override fun onResponse(
                call: Call<Boolean>, response: Response<Boolean>
            )
            {
                if(response.code() == 200){
                    sub.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun putNullSub(id : Int) {

        val request = this.repository.putNullSub(id)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            )
            {
                if(response.code() == 200){
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun putSub(id : Int) {

        val request = this.repository.putSub(id)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            )
            {
                if(response.code() == 200){
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }



    fun updateLocalizacao(id: Int, localizacao: Localizacao){
        val request = this.repository.updatelocalizaço(id, localizacao)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            )
            {
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
}