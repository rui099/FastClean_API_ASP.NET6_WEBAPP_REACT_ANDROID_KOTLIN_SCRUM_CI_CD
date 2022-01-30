package com.example.fastclean.ViewModels.Perfil

import android.media.Image
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Review.ClienteListaMedia
import com.example.fastclean.Models.Review.FuncListaMedia
import com.example.fastclean.Models.Utilizador.Cliente.ClienteDetails
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioDetails
import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.PasswordDTO
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilViewModel  (private val utilizadoresRepository: UtilizadoresRepository,
                        private val reviewsClienteRepository: ReviewClienteRepository,
                        private val reviewsFuncionarioRepository: ReviewFuncionarioRepository
                        ) : ViewModel() {

    val retrofitService  = RetrofitService.getInstance()

    val cliente = MutableLiveData<ClienteDetails>()
    val funcionario = MutableLiveData<FuncionarioDetails>()
    val estado = MutableLiveData<String>()

    val status = MutableLiveData<Boolean>()

    val userImage = MutableLiveData<Image>()
    val errorMessage = MutableLiveData<String>()






    fun getCliente(id : Int) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            val response =  utilizadoresRepository.getCliente(id)
            if (response.isSuccessful)
                cliente.postValue(response.body())
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

    fun getFuncionario(id : Int) {

        val job = GlobalScope.launch(Dispatchers.Default) {
            val response =  utilizadoresRepository.getFuncionario(id)
            if (response.isSuccessful)
                funcionario.postValue(response.body())
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

    fun atualizarMorada(id : Int, morada : Morada){
        val request = this.utilizadoresRepository.alterarMorada(id, morada)
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

    fun atualizarContacto(id : Int, contacto : Int){
        val request = this.utilizadoresRepository.alterarContacto(id, contacto)
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

    fun atualizarPassword(id : Int, passwordDTO: PasswordDTO){
        val request = this.utilizadoresRepository.alterarPassword(id, passwordDTO)
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




    fun updateEstadoIndisponivelCliente(id : Int){
        val request = this.utilizadoresRepository.updateEstadoIndisponivelCliente(id)
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

    fun updateEstadoDisponivelCliente(id : Int){
        val request = this.utilizadoresRepository.updateEstadoDisponivelCliente(id)
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



//    fun getImage(id: Int) {
//        val request = this.utilizadoresRepository.getImage(id)
//        request.enqueue(object : Callback<Image> {
//            override fun onResponse(
//                call: Call<Image>, response: Response<Image>
//            )
//            {
//                if(response.code() == 200){
//                    userImage.postValue(response.body())
//                }else{
//                    errorMessage.postValue("Erro ao carregar utilizador")
//                }
//            }
//
//            override fun onFailure(call: Call<Image>, t: Throwable) {
//                errorMessage.postValue(t.message)
//            }
//
//        })
//
//    }
}