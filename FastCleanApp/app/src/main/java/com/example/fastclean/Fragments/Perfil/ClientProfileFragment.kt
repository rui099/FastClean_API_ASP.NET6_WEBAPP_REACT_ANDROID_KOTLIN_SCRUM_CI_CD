package com.example.fastclean.Fragments.Perfil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
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
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fastclean.Adapters.ReviewClienteAdapter
import com.example.fastclean.Fragments.Registo.RegisterChoiceFragment
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Review.ClienteReview
import com.example.fastclean.Models.Review.Review
import com.example.fastclean.R
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.UnsafeOkHttpGlideModule
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Perfil.PerfilViewModel
import com.example.fastclean.ViewModels.Perfil.PerfilViewModelFactory
import com.example.fastclean.ViewModels.Review.ReviewViewModel
import com.example.fastclean.ViewModels.Review.ReviewViewModelFactory
import com.google.android.material.navigation.NavigationBarView
//import com.squareup.picasso.Picasso
import java.nio.ByteBuffer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientProfileFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    var param1: String? = null
    var param2: String? = null
    private lateinit var timeSpinner: Spinner
    lateinit var reviewClienteAdapter: ReviewClienteAdapter
    lateinit var btnEdit: ImageButton
    lateinit var nome: TextView
    lateinit var morada: TextView
    lateinit var avaliacao: RatingBar
    lateinit var contexto : Context
    private lateinit var viewModel: PerfilViewModel
    private lateinit var viewModelReview: ReviewViewModel

    private lateinit var profilePic : ImageView

    private val retrofitService = RetrofitService.getInstance()

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

        viewModel = ViewModelProvider(this, PerfilViewModelFactory(UtilizadoresRepository(retrofitService), ReviewClienteRepository(retrofitService), ReviewFuncionarioRepository(retrofitService))).get(
            PerfilViewModel::class.java)

        viewModelReview = ViewModelProvider(this, ReviewViewModelFactory( ReviewClienteRepository(retrofitService), ReviewFuncionarioRepository(retrofitService))).get(
            ReviewViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_client_profile, container, false)
        nome = view.findViewById(R.id.nomeUser)
        morada = view.findViewById(R.id.ruaUser)



        avaliacao=view.findViewById(R.id.ratingBarCliente)
        profilePic = view.findViewById(R.id.profilePic)


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


        viewModel.getCliente(UserSession.getId())


        viewModel.cliente.observe(viewLifecycleOwner, {
            nome.text = it.nome
            morada.text = it.morada.rua + " nº " + it.morada.numero + " - " + it.morada.codigoPostal + "\n" + it.morada.freguesia;
        })








        btnEdit = view.findViewById(R.id.btEdit)

        btnEdit.setOnClickListener(this)

        timeSpinner = view.findViewById(R.id.time_cliente_spinner)

        timeSpinner.onItemSelectedListener = object  :  AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

                reviewClienteAdapter.clearReviewList()
                viewModel.getCliente(UserSession.getId())

            }

            override fun onItemSelected( adapterView: AdapterView<*>?,  view: View?, position: Int, id: Long) {
                if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(0)){


                    reviewClienteAdapter.clearReviewList()
                    viewModelReview.getClienteReviews(UserSession.getId())

                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(1)){

                    reviewClienteAdapter.clearReviewList()
                    viewModelReview.getClienteReviews15(UserSession.getId())
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(2)){
                    reviewClienteAdapter.clearReviewList()
                    viewModelReview.getClienteReviews30(UserSession.getId())
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(3)){
                    reviewClienteAdapter.clearReviewList()
                    viewModelReview.getClienteReviewsTrimestre(UserSession.getId())
                }else if(adapterView?.getItemAtPosition(position) == adapterView?.getItemAtPosition(4)){
                    reviewClienteAdapter.clearReviewList()
                    viewModelReview.getClienteReviewsSemestre(UserSession.getId())
                }


                viewModelReview.clienteReviewList.observe(viewLifecycleOwner,{
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
            val fragment = DefinicoesClienteFragment()
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