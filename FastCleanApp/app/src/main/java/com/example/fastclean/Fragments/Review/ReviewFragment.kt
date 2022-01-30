package com.example.fastclean.Fragments.Review

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Fragments.Marcacao.MarcacaoDetalhesFragment
import com.example.fastclean.Fragments.Marcacao.MarcacaoFragment
import com.example.fastclean.Fragments.Reports.ReportFragment
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.R
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Review.MarcacaoReviewViewModel
import com.example.fastclean.ViewModels.Review.MarcacaoReviewViewModelFactory
import com.example.fastclean.ViewModels.Review.ReviewViewModel
import com.example.fastclean.ViewModels.Review.ReviewViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: Marcacao? = null

    private lateinit var viewModelReview : ReviewViewModel
    private lateinit var viewModelMarcacao : MarcacaoReviewViewModel
    private val retrofitService = RetrofitService.getInstance()

    lateinit var rtCotacao : RatingBar
    lateinit var btnReport : Button
    lateinit var btnSubmit: Button
    lateinit var etComentario : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt("reviewed")
            param2 = it.getSerializable("marcacao") as Marcacao
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_review, container, false)

        viewModelReview = ViewModelProvider(this, ReviewViewModelFactory(ReviewClienteRepository(retrofitService),
                                            ReviewFuncionarioRepository(retrofitService))).get(
            ReviewViewModel::class.java)

        viewModelMarcacao = ViewModelProvider(this, MarcacaoReviewViewModelFactory(MarcacoesRepository(retrofitService))).get(
            MarcacaoReviewViewModel::class.java)

        rtCotacao = v.findViewById(R.id.ratingBar)
        etComentario = v.findViewById(R.id.etComment)
        btnReport = v.findViewById(R.id.btReport)
        btnSubmit = v.findViewById(R.id.btSubmit)

        btnSubmit.setOnClickListener(this)
        btnReport.setOnClickListener(this)

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReviewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btSubmit) {

            var review = Review(etComentario.text.toString(), rtCotacao.rating, param1!!, UserSession.getId())



            if (UserSession.getRole() == "Funcionario") {
                viewModelReview.postClienteReview(review)
                viewModelReview.status.observe(viewLifecycleOwner, {

                })
                param2?.reviewCliente = true
                viewModelMarcacao.putReviewedCliente(param2!!.marcacaoId)
            } else {
                viewModelReview.postFuncionarioReview(review)
                param2?.reviewFuncionario = true
                viewModelMarcacao.putReviewedFuncionario(param2!!.marcacaoId)
            }

            Handler().postDelayed({
                val fragment = MarcacaoFragment()

                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.root_container, fragment)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }, 500)
        }

        if (v!!.id == R.id.btReport) {
            val fragment = ReportFragment()
            val bundle = Bundle()
            bundle.putInt("reported", param1!!)
            bundle.putSerializable("txt", param2)
            fragment.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }
}