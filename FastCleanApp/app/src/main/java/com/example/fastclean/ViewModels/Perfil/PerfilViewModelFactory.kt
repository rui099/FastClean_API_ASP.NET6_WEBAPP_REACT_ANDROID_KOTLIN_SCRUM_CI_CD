package com.example.fastclean.ViewModels.Perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.LoginRepository
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.ViewModels.Login.LoginViewModel

class PerfilViewModelFactory (private val utilizadoresRepository: UtilizadoresRepository,
                              private val reviewsClienteRepository: ReviewClienteRepository,
                              private val reviewsFuncionarioRepository: ReviewFuncionarioRepository
): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            PerfilViewModel(this.utilizadoresRepository,
                this.reviewsClienteRepository,
                this.reviewsFuncionarioRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}