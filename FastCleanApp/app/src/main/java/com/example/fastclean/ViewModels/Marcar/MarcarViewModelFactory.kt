package com.example.fastclean.ViewModels.Marcar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.MapaRepository
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.ViewModels.Mapa.MapaViewModel

class MarcarViewModelFactory  (private val repository: MarcacoesRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MarcarViewModel::class.java)) {
            MarcarViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}