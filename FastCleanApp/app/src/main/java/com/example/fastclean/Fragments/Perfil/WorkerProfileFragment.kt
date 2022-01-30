package com.example.fastclean.Fragments.Perfil

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fastclean.Adapters.ReviewClienteAdapter
import com.example.fastclean.Adapters.ReviewFuncAdapter
import com.example.fastclean.Fragments.Payments.PaymentFragment
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Review.ClienteReview
import com.example.fastclean.Models.Review.FuncReview
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.R
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.Repositories.SubRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Perfil.PerfilViewModel
import com.example.fastclean.ViewModels.Perfil.PerfilViewModelFactory
import com.example.fastclean.ViewModels.Perfil.SubViewModel
import com.example.fastclean.ViewModels.Perfil.SubViewModelFactory
import com.example.fastclean.ViewModels.Review.ReviewViewModel
import com.example.fastclean.ViewModels.Review.ReviewViewModelFactory


//import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkerProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkerProfileFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var timeSpinner: Spinner
    lateinit var reviewFuncAdapter: ReviewFuncAdapter
    lateinit var btnEdit: ImageButton
    private lateinit var sub: Button
    lateinit var nome: TextView
    lateinit var tipoLimpeza: TextView
    lateinit var avaliacao: RatingBar
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var viewModel: PerfilViewModel
    private lateinit var viewModelReview: ReviewViewModel

    lateinit var contexto : Context
    private lateinit var profilePic : ImageView
    private lateinit var subViewModel : SubViewModel
    lateinit var estado: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_worker_profile, container, false)

        viewModel = ViewModelProvider(this, PerfilViewModelFactory(UtilizadoresRepository(retrofitService), ReviewClienteRepository(retrofitService), ReviewFuncionarioRepository(retrofitService))).get(
            PerfilViewModel::class.java)
        subViewModel =
            ViewModelProvider(this, SubViewModelFactory(SubRepository(retrofitService))).get(
                SubViewModel::class.java
            )
        viewModelReview = ViewModelProvider(this, ReviewViewModelFactory( ReviewClienteRepository(retrofitService), ReviewFuncionarioRepository(retrofitService))).get(
            ReviewViewModel::class.java)


        nome = view.findViewById(R.id.nomeFunc)
        tipoLimpeza = view.findViewById(R.id.tipoLimpeza)

        avaliacao=view.findViewById(R.id.ratingBarFunc)
        profilePic = view.findViewById(R.id.profilePic)
        sub = view.findViewById(R.id.btnSub)
        estado = view.findViewById(R.id.btnEstado)

        subViewModel.getSub(UserSession.getId())

        subViewModel.sub.observe(viewLifecycleOwner, {

            if (it == false){
                sub.visibility = View.VISIBLE
            }
        })


        val id = UserSession.getId()

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_perfil)
            .error(R.drawable.ic_perfil)
            .override(360,360)
        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load("https://10.0.2.2:7067/api/GetImage/$id")
            .circleCrop()
            .into(profilePic)

        viewModel.getFuncionario(UserSession.getId())


        viewModel.funcionario.observe(viewLifecycleOwner, {
            nome.text = it.nome
            tipoLimpeza.text = it.morada.rua + " nº " + it.morada.numero + " - " + it.morada.codigoPostal + "\n" + it.morada.freguesia

            if(it.estado =="Indisponivel"){
                estado.text = getString(R.string.start_location_updates_button_text)
            }else{
                estado.text = getString(R.string.stop_location_updates_button_text)
            }
        })



        btnEdit = view.findViewById(R.id.btEdit)

        btnEdit.setOnClickListener(this)

        sub.setOnClickListener(this)

        timeSpinner = view.findViewById(R.id.time_spinner)

        var mainActivity = context as MainActivity


        estado.setOnClickListener {
            if(estado.text == getString(R.string.start_location_updates_button_text)){
                estado.text = getString(R.string.stop_location_updates_button_text)
                viewModel.updateEstadoDisponivelCliente(UserSession.getId())
                mainActivity.ativarLocalizacao()
            }else{
                estado.text = getString(R.string.start_location_updates_button_text)
                viewModel.updateEstadoIndisponivelCliente(UserSession.getId())
                mainActivity.desativarLocalizacao()
            }
        }



        timeSpinner.onItemSelectedListener = object  :  AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                reviewFuncAdapter.clearReviewList()
                viewModelReview.getFuncReviews(UserSession.getId())


            }

            override fun onItemSelected( adapterView: AdapterView<*>?,  view: View?, position: Int, id: Long) {
                if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(0)){
                    reviewFuncAdapter.clearReviewList()
                    viewModelReview.getFuncReviews(UserSession.getId())

                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(1)){
                    reviewFuncAdapter.clearReviewList()
                    viewModelReview.getFuncReviews15(UserSession.getId())
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(2)){
                    reviewFuncAdapter.clearReviewList()
                    viewModelReview.getFuncReviews30(UserSession.getId())
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(3)){
                    reviewFuncAdapter.clearReviewList()
                    viewModelReview.getFuncReviewsTrimestre(UserSession.getId())
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(4)){
                    reviewFuncAdapter.clearReviewList()
                    viewModelReview.getFuncReviewsSemestre(UserSession.getId())
                }

                viewModelReview.funcionarioReviewList.observe(viewLifecycleOwner,{
                    avaliacao.rating = it.media
                    reviewFuncAdapter.setReviewList(it.listaReviews as ArrayList<FuncReview>)
                    reviewFuncAdapter.notifyDataSetChanged()
                }
                )

            }
        }


        ArrayAdapter.createFromResource(
            contexto,
            R.array.time_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            timeSpinner.adapter = adapter
        }


        this.reviewFuncAdapter = ReviewFuncAdapter(contexto)


        var recyclerV = view.findViewById(R.id.rv_Review) as RecyclerView
        recyclerV.layoutManager = LinearLayoutManager(activity)
        recyclerV.adapter = this.reviewFuncAdapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkerProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            WorkerProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btEdit){
            val fragment = DefinicoesFuncionarioFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()


        }

        if(v.id == R.id.btnSub) {
            val fragment = PaymentFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }

    interface atualizarLocalizacao{
        fun ativarLocalizacao()

        fun desativarLocalizacao()
    }
}