package com.example.fastclean.ViewModels.Mapa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.MapaRepository

class MapaViewModelFactory  (private val repository: MapaRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapaViewModel::class.java)) {
            MapaViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}