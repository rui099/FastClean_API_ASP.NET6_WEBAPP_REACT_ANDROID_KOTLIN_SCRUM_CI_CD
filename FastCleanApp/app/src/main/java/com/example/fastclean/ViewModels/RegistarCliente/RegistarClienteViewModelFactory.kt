package com.example.fastclean.ViewModels.RegistarCliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModel

class RegistarClienteViewModelFactory  (private val repository: UtilizadoresRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegistarClienteViewModel::class.java)) {
            RegistarClienteViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}