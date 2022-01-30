package com.example.fastclean.Fragments.Perfil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
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
import com.example.fastclean.Models.Review.ClienteReview

import com.example.fastclean.Models.Review.FuncReview
import com.example.fastclean.R
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Perfil.PerfilViewModel
import com.example.fastclean.ViewModels.Perfil.PerfilViewModelFactory
import com.example.fastclean.ViewModels.Review.ReviewViewModel
import com.example.fastclean.ViewModels.Review.ReviewViewModelFactory

//import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VistaPerfilClienteFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    var param1: String? = null
    var param2: String? = null
    private lateinit var timeSpinner: Spinner
    lateinit var reviewClienteAdapter: ReviewClienteAdapter
    lateinit var nome: TextView
    lateinit var morada: TextView
    lateinit var avaliacao: RatingBar
    lateinit var contexto : Context
    private lateinit var viewModel: PerfilViewModel
    private lateinit var reviewViewModel: ReviewViewModel
    private lateinit var profilePic : ImageView
    private var idC: Int? = null
    private var idFunc: String? = null

    private val retrofitService = RetrofitService.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idFunc = it.getSerializable("id") as String?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, PerfilViewModelFactory(UtilizadoresRepository(retrofitService), ReviewClienteRepository(retrofitService), ReviewFuncionarioRepository(retrofitService))).get(
            PerfilViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_vista_perfil, container, false)
        nome = view.findViewById(R.id.nomeUser)
        morada = view.findViewById(R.id.ruaUser)
        viewModel.getCliente(UserSession.getId())
        avaliacao=view.findViewById(R.id.ratingBarCliente)
        profilePic = view.findViewById(R.id.profilePic)
        idC = idFunc?.toInt()
        reviewViewModel = ViewModelProvider(this, ReviewViewModelFactory(ReviewClienteRepository(retrofitService),
            ReviewFuncionarioRepository(retrofitService))
        ).get(
            ReviewViewModel::class.java)


        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_perfil)
            .error(R.drawable.ic_perfil)
            .override(360,360)
        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load("https://10.0.2.2:7067/api/GetImage/$idC")
            .circleCrop()
            .into(profilePic)

        viewModel.getCliente(idC!!)

        viewModel.cliente.observe(viewLifecycleOwner, {
            nome.text = it.nome
            morada.text = it.morada.rua + " nÂº " + it.morada.numero + " - " + it.morada.codigoPostal + "\n" + it.morada.freguesia;
        })



        timeSpinner = view.findViewById(R.id.time_spinner_cliente)



        timeSpinner.onItemSelectedListener = object  :  AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                reviewClienteAdapter.clearReviewList()
                idC?.let { reviewViewModel.getClienteReviews(it) }


            }

            override fun onItemSelected( adapterView: AdapterView<*>?,  view: View?, position: Int, id: Long) {
                if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(0)){
                    reviewClienteAdapter.clearReviewList()
                    idC?.let { reviewViewModel.getClienteReviews(it) }
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(1)){

                    reviewClienteAdapter.clearReviewList()
                    idC?.let { reviewViewModel.getClienteReviews15(it) }
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(2)){
                    reviewClienteAdapter.clearReviewList()
                    idC?.let { reviewViewModel.getClienteReviews30(it) }
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(3)){
                    reviewClienteAdapter.clearReviewList()
                    idC?.let { reviewViewModel.getClienteReviewsTrimestre(it) }
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(4)){
                    reviewClienteAdapter.clearReviewList()
                    idC?.let { reviewViewModel.getClienteReviewsSemestre(it) }
                }

                reviewViewModel.clienteReviewList.observe(viewLifecycleOwner,{
                    avaliacao.rating = it.media.toFloat()
                    reviewClienteAdapter.setReviewList(it.listaReviews as ArrayList<ClienteReview>)
                    reviewClienteAdapter.notifyDataSetChanged()
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

        print(timeSpinner.selectedItem.toString())

        this.reviewClienteAdapter = ReviewClienteAdapter(contexto)


        var recyclerV = view.findViewById(R.id.rv_Review) as RecyclerView
        recyclerV.layoutManager = LinearLayoutManager(activity)
        recyclerV.adapter = this.reviewClienteAdapter

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ClientProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btEdit){
            val bundle = Bundle()
            bundle.putString("role", "client")
            val fragment = ProfileEditFragment()
            fragment.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }

    fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }
}