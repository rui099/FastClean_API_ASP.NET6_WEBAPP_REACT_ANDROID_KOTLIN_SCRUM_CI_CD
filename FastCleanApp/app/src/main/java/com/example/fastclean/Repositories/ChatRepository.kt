package com.example.fastclean.Repositories

import androidx.lifecycle.LiveData
import com.example.fastclean.Models.Chat.Chat
import com.example.fastclean.Models.Chat.Mensagem
import com.example.fastclean.Models.Chat.NewChat
import com.example.fastclean.Models.Chat.SendMensagem
import com.example.fastclean.Models.Login.Login
import com.example.fastclean.RestService.RetrofitService
import retrofit2.Call

class ChatRepository constructor(private val retrofitService : RetrofitService) {

    fun postChat(chat : NewChat) = retrofitService.postChat(chat)

//    fun sendMessage(idChat: Int, idSender: Int, mensagem: SendMensagem) = retrofitService.sendMessage(idChat, idSender, mensagem)

    fun getChat(id: Int) : Call<ArrayList<Chat>> = retrofitService.getChat(id)

    fun deleteChat(idCliente: Int, idFuncionario: Int) = retrofitService.deleteChat(idCliente,idFuncionario)

//    fun getChatMessages(id: Int) : Call<Chat> = retrofitService.getChatMessages(id)
}