package com.example.fastclean.Repositories

import com.example.fastclean.Models.Review.FuncReview
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.RestService.RetrofitService

class ReviewFuncionarioRepository constructor(private val retrofitService : RetrofitService) {

    fun getFuncReviews(id : Int) = retrofitService.getFuncReviews(id)

    fun getFuncReviews30(id : Int) = retrofitService.getFuncReviews30(id)

    fun getFuncReviews15(id : Int) = retrofitService.getFuncReviews15(id)

    fun getFuncReviewsSemestre(id : Int) = retrofitService.getFuncReviewsSemestre(id)

    fun getFuncReviewsTrimestre(id : Int) = retrofitService.getFuncReviewsTrimestre(id)

    fun postFuncionarioReview(review : Review) = retrofitService.postReviewFunc(review)


}