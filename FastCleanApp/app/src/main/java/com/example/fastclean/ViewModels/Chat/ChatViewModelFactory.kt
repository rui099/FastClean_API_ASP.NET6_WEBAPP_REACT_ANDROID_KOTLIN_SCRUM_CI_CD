package com.example.fastclean.ViewModels.Chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.Repositories.MarcacoesRepository

class ChatViewModelFactory(private val repository: ChatRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            ChatViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}