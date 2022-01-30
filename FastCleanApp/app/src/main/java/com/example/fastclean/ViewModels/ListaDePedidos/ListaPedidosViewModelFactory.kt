package com.example.fastclean.ViewModels.ListaDePedidos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModel

class ListaPedidosViewModelFactory(private val repository: MarcacoesRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ListaPedidosViewModel::class.java)) {
            ListaPedidosViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}
