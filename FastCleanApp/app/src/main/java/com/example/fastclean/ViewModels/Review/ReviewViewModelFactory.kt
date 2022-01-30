package com.example.fastclean.ViewModels.Review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository


class ReviewViewModelFactory (private val reviewsClienteRepository: ReviewClienteRepository,
                              private val reviewsFuncionarioRepository: ReviewFuncionarioRepository
): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            ReviewViewModel(this.reviewsClienteRepository,
                this.reviewsFuncionarioRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}