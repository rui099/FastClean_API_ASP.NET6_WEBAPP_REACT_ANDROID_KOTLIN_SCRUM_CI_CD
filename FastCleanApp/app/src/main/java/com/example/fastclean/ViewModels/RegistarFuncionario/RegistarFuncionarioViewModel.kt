package com.example.fastclean.ViewModels.RegistarFuncionario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Utilizador.Cliente.ClienteRegister
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioRegister
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistarFuncionarioViewModel (private val repository: UtilizadoresRepository) : ViewModel() {
    val retrofitService  = RetrofitService.getInstance()
    val status = MutableLiveData<Boolean>()

    fun saveRecipe(funcionario: FuncionarioRegister){
    val request = this.repository.saveFuncionario(funcionario)
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
}