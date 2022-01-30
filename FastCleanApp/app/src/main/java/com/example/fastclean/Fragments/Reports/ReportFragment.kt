package com.example.fastclean.Fragments.Reports

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
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Reports.Report
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.R
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.Repositories.ReportsRepository
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Reports.ReportViewModel
import com.example.fastclean.ViewModels.Reports.ReportViewModelFactory
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
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: Marcacao? = null

    private lateinit var viewModelReport : ReportViewModel
    private lateinit var viewModelMarcacao : MarcacaoReviewViewModel
    private val retrofitService = RetrofitService.getInstance()

    lateinit var etAssunto : EditText
    lateinit var btnSubmit: Button
    lateinit var etDesc : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt("reported")
            param2 = it.getSerializable("txt") as Marcacao?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_report, container, false)

        viewModelReport = ViewModelProvider(this, ReportViewModelFactory(ReportsRepository(retrofitService))).get(
            ReportViewModel::class.java)

        viewModelMarcacao = ViewModelProvider(this, MarcacaoReviewViewModelFactory(
            MarcacoesRepository(retrofitService)
        )
        ).get(
            MarcacaoReviewViewModel::class.java)

        etAssunto = v.findViewById(R.id.etAssunto)
        etDesc = v.findViewById(R.id.etDesc)
        btnSubmit = v.findViewById(R.id.btSubmit)

        btnSubmit.setOnClickListener(this)

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReportFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReportFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btSubmit) {

            var report = Report(etAssunto.text.toString(), etDesc.text.toString(), param1!!, UserSession.getId())

            Log.e("report", report.toString())

            if (UserSession.getRole() == "Cliente") {
                viewModelReport.reportFuncionario(report)
                param2?.reviewCliente = true
                viewModelMarcacao.putReviewedCliente(param2!!.marcacaoId)
            } else {
                viewModelReport.reportCliente(report)
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
    }
}