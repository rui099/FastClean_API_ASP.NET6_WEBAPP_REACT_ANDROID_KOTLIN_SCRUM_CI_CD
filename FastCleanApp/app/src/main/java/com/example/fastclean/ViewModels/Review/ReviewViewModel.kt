package com.example.fastclean.ViewModels.Review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Models.Chat.Chat
import com.example.fastclean.Models.Chat.SendMensagem
import com.example.fastclean.Models.Review.ClienteListaMedia
import com.example.fastclean.Models.Review.FuncListaMedia
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.RestService.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class ReviewViewModel(private val reviewsClienteRepository: ReviewClienteRepository,
                      private val reviewsFuncionarioRepository: ReviewFuncionarioRepository
) : ViewModel() {
    val retrofitService = RetrofitService.getInstance()
    val status = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val clienteReviewList = MutableLiveData<ClienteListaMedia>()
    val funcionarioReviewList = MutableLiveData<FuncListaMedia>()


    fun postClienteReview(review : Review){
        val request = this.reviewsClienteRepository.postClienteReview(review)
        request.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    status.postValue( true)
                } else {
                    status.postValue( false)
                    errorMessage.postValue("Erro a mandar review")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun postFuncionarioReview(review : Review){
        val request = this.reviewsFuncionarioRepository.postFuncionarioReview(review)
        request.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    status.postValue( true)
                } else {
                    status.postValue( false)
                    errorMessage.postValue("Erro a mandar review")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
    fun getClienteReviews30(id : Int) {

        val request = this.reviewsClienteRepository.getClienteReviews30(id)
        request.enqueue(object : Callback<ClienteListaMedia> {
            override fun onResponse(
                call: Call<ClienteListaMedia>, response: Response<ClienteListaMedia>
            )
            {
                if(response.code() == 200){
                    clienteReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<ClienteListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getClienteReviews15(id : Int) {

        val request = this.reviewsClienteRepository.getClienteReviews15(id)
        request.enqueue(object : Callback<ClienteListaMedia> {
            override fun onResponse(
                call: Call<ClienteListaMedia>, response: Response<ClienteListaMedia>
            )
            {
                if(response.code() == 200){
                    clienteReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<ClienteListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getClienteReviewsTrimestre(id : Int) {

        val request = this.reviewsClienteRepository.getClienteReviewsTrimestre(id)
        request.enqueue(object : Callback<ClienteListaMedia> {
            override fun onResponse(
                call: Call<ClienteListaMedia>, response: Response<ClienteListaMedia>
            )
            {
                if(response.code() == 200){
                    clienteReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<ClienteListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getClienteReviewsSemestre(id : Int) {

        val request = this.reviewsClienteRepository.getClienteReviewsSemestre(id)
        request.enqueue(object : Callback<ClienteListaMedia> {
            override fun onResponse(
                call: Call<ClienteListaMedia>, response: Response<ClienteListaMedia>
            )
            {
                if(response.code() == 200){
                    clienteReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<ClienteListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getClienteReviews(id : Int) {

        val request = this.reviewsClienteRepository.getClienteReviews(id)
        request.enqueue(object : Callback<ClienteListaMedia> {
            override fun onResponse(
                call: Call<ClienteListaMedia>, response: Response<ClienteListaMedia>
            )
            {
                if(response.code() == 200){
                    clienteReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<ClienteListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }
    fun getFuncReviews(id : Int) {

        val request = this.reviewsFuncionarioRepository.getFuncReviews(id)
        request.enqueue(object : Callback<FuncListaMedia> {
            override fun onResponse(
                call: Call<FuncListaMedia>, response: Response<FuncListaMedia>
            )
            {
                if(response.code() == 200){
                    funcionarioReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<FuncListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getFuncReviews30(id : Int) {

        val request = this.reviewsFuncionarioRepository.getFuncReviews30(id)
        request.enqueue(object : Callback<FuncListaMedia> {
            override fun onResponse(
                call: Call<FuncListaMedia>, response: Response<FuncListaMedia>
            )
            {
                if(response.code() == 200){
                    funcionarioReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<FuncListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getFuncReviews15(id : Int) {

        val request = this.reviewsFuncionarioRepository.getFuncReviews15(id)
        request.enqueue(object : Callback<FuncListaMedia> {
            override fun onResponse(
                call: Call<FuncListaMedia>, response: Response<FuncListaMedia>
            )
            {
                if(response.code() == 200){
                    funcionarioReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<FuncListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getFuncReviewsTrimestre(id : Int) {

        val request = this.reviewsFuncionarioRepository.getFuncReviewsTrimestre(id)
        request.enqueue(object : Callback<FuncListaMedia> {
            override fun onResponse(
                call: Call<FuncListaMedia>, response: Response<FuncListaMedia>
            )
            {
                if(response.code() == 200){
                    funcionarioReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<FuncListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

    fun getFuncReviewsSemestre(id : Int) {

        val request = this.reviewsFuncionarioRepository.getFuncReviewsSemestre(id)
        request.enqueue(object : Callback<FuncListaMedia> {
            override fun onResponse(
                call: Call<FuncListaMedia>, response: Response<FuncListaMedia>
            )
            {
                if(response.code() == 200){
                    funcionarioReviewList.postValue( response.body())
                }else{
                    errorMessage.postValue("Erro ao mostrar reviews")
                }
            }

            override fun onFailure(call: Call<FuncListaMedia>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })

    }

}