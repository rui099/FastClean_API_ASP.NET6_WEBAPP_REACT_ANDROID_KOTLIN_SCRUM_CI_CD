package com.example.fastclean.Repositories

import com.example.fastclean.Models.Review.Review
import com.example.fastclean.RestService.RetrofitService

class ReviewClienteRepository constructor(private val retrofitService : RetrofitService) {

    fun getClienteReviews(id : Int) = retrofitService.getClienteReviews(id)

    fun getClienteReviews30(id : Int) = retrofitService.getClienteReviews30(id)

    fun getClienteReviews15(id : Int) = retrofitService.getClienteReviews15(id)

    fun getClienteReviewsSemestre(id : Int) = retrofitService.getClienteReviewsSemestre(id)

    fun getClienteReviewsTrimestre(id : Int) = retrofitService.getClienteReviewsTrimestre(id)

    fun postClienteReview(review : Review) = retrofitService.postReviewCliente(review)
}