package com.example.fastclean.Adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fastclean.Models.Review.FuncReview

import com.example.fastclean.R

class ReviewFuncAdapter(context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var context = context
    private var reviews = ArrayList<FuncReview>()

    /**
     * removes all items from adapter list
     */
    fun clearReviewList(){
        reviews.clear()
        notifyDataSetChanged()
    }

    /**
     * Updates the adapter list
     * @param reviewList new adapter list.
     */
    fun setReviewList(reviewList: ArrayList<FuncReview>){
        this.reviews = reviewList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.review_rv_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReviewViewHolder -> {
                holder.bind(reviews[position])
            }
        }

        //tive que passar o context para o adapter como parametro e transforma-lo em context, no fragment
        // tive que usar o on attach para ir buscar o context da main ativity


    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ReviewViewHolder constructor(
        reviewView : View
    ) : RecyclerView.ViewHolder(reviewView){

        private val from = reviewView.findViewById<TextView>(R.id.from)
        private val comment = reviewView.findViewById<TextView>(R.id.comment)
        private val date = reviewView.findViewById<TextView>(R.id.date)
        private val ratingBar= reviewView.findViewById<RatingBar>(R.id.ratingBar)



        fun bind (review: FuncReview){

            from.text = review.reviewerName
            comment.text = review.comentario
            ratingBar.rating = review.cotacao.toFloat()


        }


    }





}