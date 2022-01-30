package com.example.fastclean.ViewModels.Login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Login.Login
import com.example.fastclean.Models.Login.UserInfo
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Repositories.LoginRepository
import com.example.fastclean.RestService.RetrofitService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {
    val retrofitService  = RetrofitService.getInstance()
    val userInfo = MutableLiveData<UserInfo>()
    val errorMessage = MutableLiveData<String>()


    fun login(login: Login) {

        val request = this.repository.login(login)
        request.enqueue(object : Callback<UserInfo> {
            override fun onResponse(
                call: Call<UserInfo>, response: Response<UserInfo>
            ) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    userInfo.postValue(response.body())
                } else {
                    errorMessage.postValue("User ou password errada")
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }
}