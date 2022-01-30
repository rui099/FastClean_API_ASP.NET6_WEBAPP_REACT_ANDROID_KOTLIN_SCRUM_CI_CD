package com.example.fastclean.ViewModels.DetalhesDeMarcacao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.MarcacoesRepository

class DetalhesViewModelFactory(private val repository: MarcacoesRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetalhesViewModel::class.java)) {
            DetalhesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}