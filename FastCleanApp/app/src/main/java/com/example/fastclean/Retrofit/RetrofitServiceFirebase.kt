package com.example.fastclean.RestService


import com.example.fastclean.Models.Firebase.PushNotification

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitServiceFirebase  {


    //Notificações

    @Headers("Authorization: key=AAAAgAAFv5k:APA91bETPs60jcqw5cJnbUdoj2uvsNuGSQQN52X1DfAFfqpZ9XzEvOaIFD066WmPJ4gl8aKakUgpqSO89SlJU08-a62Jmrn71ycVXSWBRTEzayKFryZWN4mrMTJn3wJITfRUUzZzATBV",
        "Content-Type: application/json")
    @POST("fcm/send" )
    suspend fun postNotification(@Body notification: PushNotification): Response<ResponseBody>


    companion object {
        private val retrofitService: RetrofitServiceFirebase by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitServiceFirebase::class.java)

        }

        fun getInstance(): RetrofitServiceFirebase {
            return retrofitService
        }



    }
}


