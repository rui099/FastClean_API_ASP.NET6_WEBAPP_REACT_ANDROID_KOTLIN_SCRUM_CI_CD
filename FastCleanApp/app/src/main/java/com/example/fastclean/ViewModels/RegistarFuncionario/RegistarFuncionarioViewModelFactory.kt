package com.example.fastclean.ViewModels.RegistarFuncionario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModel
import com.example.fastclean.ViewModels.RegistarCliente.RegistarClienteViewModel

class RegistarFuncionarioViewModelFactory  (private val repository: UtilizadoresRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RegistarFuncionarioViewModel::class.java)) {
            RegistarFuncionarioViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}