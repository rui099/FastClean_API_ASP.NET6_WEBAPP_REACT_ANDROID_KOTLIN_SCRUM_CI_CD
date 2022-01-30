package com.example.fastclean.ViewModels.Reports

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Reports.Report
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.Repositories.ReportsRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class ReportViewModel (private val repository: ReportsRepository) : ViewModel(){
    val retrofitService  = RetrofitService.getInstance()
    val errorMessage = MutableLiveData<String>()

    fun reportCliente(report : Report){
        val request = this.repository.reportCliente(report)
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

    fun reportFuncionario(report : Report){
        val request = this.repository.reportFuncionario(report)
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

    fun reportClienteChat(report : Report){
        val request = this.repository.reportClienteChat(report)
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

    fun reportFuncionarioChat(report : Report){
        val request = this.repository.reportFuncionarioChat(report)
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