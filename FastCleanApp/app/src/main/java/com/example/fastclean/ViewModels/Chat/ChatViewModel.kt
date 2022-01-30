package com.example.fastclean.ViewModels.Chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fastclean.Adapters.ChatAdapter
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Chat.Chat
import com.example.fastclean.Models.Chat.Mensagem
import com.example.fastclean.Models.Chat.NewChat
import com.example.fastclean.Models.Chat.SendMensagem
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection

class ChatViewModel (private val repository: ChatRepository) : ViewModel(){
    val retrofitService  = RetrofitService.getInstance()
    val status = MutableLiveData<Boolean>()
    val mChat = MutableLiveData<ArrayList<Chat>>()
//    val mlist = MutableLiveData<Chat>()
//    val mMensagem = MutableLiveData<SendMensagem>()
    val errorMessage = MutableLiveData<String>()

    fun getChat(id: Int) : MutableLiveData<ArrayList<Chat>>{
        val request = this.repository.getChat(id)
        request.enqueue(object: Callback<ArrayList<Chat>>{
            override fun onResponse(call: Call<ArrayList<Chat>>, response: Response<ArrayList<Chat>>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    mChat.postValue(response.body())
                } else {
                    errorMessage.postValue("Não foi possível carregar mensagens")
                }
            }

            override fun onFailure(call: Call<ArrayList<Chat>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
        return mChat
    }

    fun postChat(chat : NewChat) {
        val request = this.repository.postChat(chat)
        request.enqueue(object: Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                } else {
                    errorMessage.postValue("Não foi possível carregar mensagens")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun deleteChat(idCliente: Int, idFuncionario: Int) {
        val request = this.repository.deleteChat(idCliente,idFuncionario)
        request.enqueue(object: Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                } else {
                    errorMessage.postValue("Não foi possível carregar mensagens")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

//    fun getChatMessages(id: Int) : MutableLiveData<Chat>{
//        val request = this.repository.getChatMessages(id)
//        request.enqueue(object: Callback<Chat>{
//            override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
//                if (response.code() == HttpURLConnection.HTTP_OK) {
//                    mlist.postValue(response.body())
//                } else {
//                    errorMessage.postValue("Não foi possível carregar mensagens")
//                }
//            }
//
//            override fun onFailure(call: Call<Chat>, t: Throwable) {
//                errorMessage.postValue(t.message)
//            }
//
//        })
//        return mlist
//    }

//    fun sendMessage(idChat: Int, idSender: Int, mensagem: SendMensagem){
//        val request = this.repository.sendMessage(idChat, idSender, mensagem)
//        request.enqueue(object: Callback<SendMensagem> {
//            override fun onResponse(call: Call<SendMensagem>, response: Response<SendMensagem>) {
//                if (response.code() == HttpURLConnection.HTTP_OK) {
//                    mMensagem.postValue(response.body())
//                } else {
//                    errorMessage.postValue("Erro a mandar mensagem")
//                }
//            }
//
//            override fun onFailure(call: Call<SendMensagem>, t: Throwable) {
//                errorMessage.postValue(t.message)
//            }
//        })
//    }





}
