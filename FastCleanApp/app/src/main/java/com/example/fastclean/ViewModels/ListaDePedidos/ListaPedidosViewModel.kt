package com.example.fastclean.ViewModels.ListaDePedidos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaPedidosViewModel (private val repository: MarcacoesRepository) : ViewModel() {
    val retrofitService  = RetrofitService.getInstance()
    val pedidosList = MutableLiveData<List<Marcacao>>()
    val errorMessage = MutableLiveData<String>()
    val status = MutableLiveData<Boolean>()



    fun getPedidos(id : Int) {
        val request = this.repository.getPedidos(id)
        request.enqueue(object : Callback<List<Marcacao>> {
            override fun onResponse(
                call: Call<List<Marcacao>>, response: Response<List<Marcacao>>
            ) {
                if(response.code() == 200){
                    pedidosList.postValue( response.body())
                }else{
                    errorMessage.postValue("Não foi possivel encontrar pedidos")
                }
            }

            override fun onFailure(call: Call<List<Marcacao>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun putAceitarPedido(id : Int,idFuncionario:Int) {
        val request = this.repository.putAceitarPedido(id,idFuncionario)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            ) {
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
    fun rejeitarPedido(id : Int,idFuncionario:Int) {
        val request = this.repository.rejeitarPedido(id,idFuncionario)
        request.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            ) {
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