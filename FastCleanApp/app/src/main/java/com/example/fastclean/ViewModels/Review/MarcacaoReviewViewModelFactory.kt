package com.example.fastclean.ViewModels.Review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.ViewModels.DetalhesDeMarcacao.DetalhesViewModel

class MarcacaoReviewViewModelFactory(private val repository: MarcacoesRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MarcacaoReviewViewModel::class.java)) {
            MarcacaoReviewViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}