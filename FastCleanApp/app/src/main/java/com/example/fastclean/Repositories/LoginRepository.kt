package com.example.fastclean.Repositories

import com.example.fastclean.Models.Login.Login
import com.example.fastclean.RestService.RetrofitService

class LoginRepository constructor(private val retrofitService : RetrofitService) {

    fun login(login : Login) = retrofitService.login(login)

}