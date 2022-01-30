package com.example.fastclean.ViewModels.Mapa

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioDetails
import com.example.fastclean.Repositories.MapaRepository

import com.example.fastclean.RestService.RetrofitService
import kotlinx.coroutines.*


class MapaViewModel (private val repository: MapaRepository) : ViewModel() {
    val retrofitService  = RetrofitService.getInstance()
    val lista = MutableLiveData<List<FuncionarioDetails>>()
    val errorMessage = MutableLiveData<String>()

 fun getFuncRangePNormal(latitude : Double, longitude : Double, range : Int) {
     val job = GlobalScope.launch(Dispatchers.Default) {
             val response = MapaRepository(retrofitService).getFuncRangePNormal(
                 latitude!!.toDouble(),
                 longitude!!.toDouble(),
                 range
             )

             if (response.isSuccessful)

                 lista.postValue(response.body())
             else {
                 errorMessage.postValue("Não foi possivel encontrar funcionarios")
             }
         Log.d("TAG", "Job is Running")
         }

         runBlocking {
             // waiting for the coroutine to finish it's work
             job.join()
             Log.d("TAG", "Main Thread is Running")
         }

     }

    fun getFuncRangeProfissional(latitude : Double, longitude : Double, range : Int) {
        val job = GlobalScope.launch(Dispatchers.Default) {

            val response = MapaRepository(retrofitService).getFuncRangeProfissional(
                latitude!!.toDouble(),
                longitude!!.toDouble(),
                range
            )
            if (response.isSuccessful)
                lista.postValue(response.body())
            else {
                errorMessage.postValue("Não foi possivel encontrar funcionarios")
            }
        }
        runBlocking {
            // waiting for the coroutine to finish it's work
            job.join()
            Log.d("TAG", "Main Thread is Running")
        }
    }
/*
    fun getFuncRangeProfissional(latitude : Double, longitude : Double, range : Int) {

        val request = this.repository.getFuncRangeProfissional(latitude,longitude,range)
        request.enqueue(object : Callback<List<FuncionarioDetails>> {
            override fun onResponse(
                call: Call<List<FuncionarioDetails>>, response: Response<List<FuncionarioDetails>>
            ) {
                if(response.code() == 200){
                    lista.postValue( response.body())
                }else{
                    errorMessage.postValue("Não foi possivel encontrar funcionarios")
                }
            }

            override fun onFailure(call: Call<List<FuncionarioDetails>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getFuncRangePNormal(latitude : Double, longitude : Double, range : Int) {

        val request = this.repository.getFuncRangePNormal(latitude,longitude,range)
        request.enqueue(object : Callback<List<FuncionarioDetails>> {
            override fun onResponse(
                call: Call<List<FuncionarioDetails>>, response: Response<List<FuncionarioDetails>>
            ) {
                if(response.code() == 200){
                    lista.postValue( response.body())
                }else{
                    errorMessage.postValue("Não foi possivel encontrar funcionarios")
                }
            }

            override fun onFailure(call: Call<List<FuncionarioDetails>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }
*/
}