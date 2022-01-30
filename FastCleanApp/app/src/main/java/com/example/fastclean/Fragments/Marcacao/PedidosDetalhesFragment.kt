package com.example.fastclean.Fragments.Marcacao

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fastclean.Fragments.Marcar.MarcarAgoraFragment
import com.example.fastclean.Fragments.Perfil.VistaPerfilClienteFragment
import com.example.fastclean.Fragments.Review.ReviewFragment
import com.example.fastclean.Models.Firebase.Notification
import com.example.fastclean.Models.Firebase.PushNotification
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.R
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.RestService.RetrofitServiceFirebase
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Chat.ChatViewModel
import com.example.fastclean.ViewModels.Chat.ChatViewModelFactory
import com.example.fastclean.ViewModels.DetalhesDeMarcacao.DetalhesViewModel
import com.example.fastclean.ViewModels.DetalhesDeMarcacao.DetalhesViewModelFactory
import com.example.fastclean.ViewModels.ListaDePedidos.ListaPedidosViewModel
import com.example.fastclean.ViewModels.ListaDePedidos.ListaPedidosViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PedidosDetalhesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PedidosDetalhesFragment : Fragment(),View.OnClickListener  {
    // TODO: Rename and change types of parameters
    private var param1: Marcacao? = null

    lateinit var btAceitar : Button
    lateinit var btRecusar : Button
    lateinit var verMap : Button

    private lateinit var viewModel: ListaPedidosViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var chatviewModel: ChatViewModel
    private lateinit var profilePicFunc : ImageView
    private lateinit var profilePicCliente : ImageView
    private lateinit var showC : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable("txt") as Marcacao

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pedidos_detalhes, container, false)

        viewModel = ViewModelProvider(this, ListaPedidosViewModelFactory(MarcacoesRepository(retrofitService))).get(
            ListaPedidosViewModel::class.java)

        showC = view.findViewById(R.id.showC)
        btAceitar = view.findViewById(R.id.btAceitar)
        btRecusar = view.findViewById(R.id.btRecusar)
        verMap = view.findViewById(R.id.btVerMapa)
        profilePicFunc = view.findViewById(R.id.FuncP)
        profilePicCliente = view.findViewById(R.id.ClienteP)
        chatviewModel = ViewModelProvider(this, ChatViewModelFactory(ChatRepository(retrofitService))).get(
            ChatViewModel::class.java)

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_perfil)
            .error(R.drawable.ic_perfil)
            .override(360,360)
        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load("https://10.0.2.2:7067/api/GetImage/${param1?.funcionarioID}")
            .circleCrop()
            .into(profilePicFunc)


        val requestOptions2 = RequestOptions()
            .placeholder(R.drawable.ic_perfil)
            .error(R.drawable.ic_perfil)
            .override(360,360)
        Glide.with(this)
            .applyDefaultRequestOptions(requestOptions)
            .load("https://10.0.2.2:7067/api/GetImage/${param1?.clienteID}")
            .circleCrop()
            .into(profilePicCliente)

        view.findViewById<TextView>(R.id.cliente).text = param1?.cliente
        view.findViewById<TextView>(R.id.funcionario).text = param1?.funcionario
        view.findViewById<TextView>(R.id.data).text = param1?.diaHora
        view.findViewById<TextView>(R.id.numHoras).text = param1?.numHorasPrevistas.toString()
        view.findViewById<TextView>(R.id.TipoImovel).text = param1?.tipoImovel
        view.findViewById<TextView>(R.id.NumQuartos).text = param1?.numQuartos.toString()
        view.findViewById<TextView>(R.id.NumCBanho).text = param1?.numCasasDeBanho.toString()
        view.findViewById<TextView>(R.id.Morada).text = param1?.moradaMarcacao.toString()
        if(param1?.sala == false)
            view.findViewById<CheckedTextView>(R.id.cv_Sala).setCheckMarkDrawable(R.drawable.blue_uncheck)
        if(param1?.cozinha == false)
            view.findViewById<CheckedTextView>(R.id.cv_Cozinha).setCheckMarkDrawable(R.drawable.blue_uncheck)
        view.findViewById<TextView>(R.id.Limpeza).text = param1?.tipoLimpeza
        view.findViewById<TextView>(R.id.Agendamento).text = param1?.tipoAgendamento
        if(param1?.terminada == false)
            view.findViewById<CheckedTextView>(R.id.cv_Terminada).setCheckMarkDrawable(R.drawable.blue_uncheck)
        if(param1?.marcacaoAceitePeloFunc == false)
            view.findViewById<CheckedTextView>(R.id.cv_AceiteFunc).setCheckMarkDrawable(R.drawable.blue_uncheck)
        if(param1?.marcacaoAceitePeloCliente == false)
            view.findViewById<CheckedTextView>(R.id.cv_AceiteCliente).setCheckMarkDrawable(R.drawable.blue_uncheck)

        btAceitar.setOnClickListener(this)
        btRecusar.setOnClickListener(this)
        verMap.setOnClickListener(this)
        showC.setOnClickListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarcacaoDetalhesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarcacaoDetalhesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.btAceitar) {
            viewModel.putAceitarPedido(param1!!.marcacaoId, UserSession.getId())
            Toast.makeText(activity, "Aceite com sucesso", Toast.LENGTH_SHORT).show()

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()

            val f2 = MarcacaoFragment()
            fragmentTransaction?.replace(R.id.root_container, f2)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

            var token = param1?.clienteID.toString()
            val notification = Notification("Pedido de marcação a: ${param1?.funcionario}",
                "horario: ${param1!!.diaHora}, Foi aceite!")
            val notificacao = PushNotification(
                notification,
                "/topics/$token"
            )
            sendNotification(notificacao)
        }
        if (v!!.id == R.id.btRecusar) {
            viewModel.rejeitarPedido(param1!!.marcacaoId, UserSession.getId())
            Toast.makeText(activity, "Rejeitada com sucesso", Toast.LENGTH_SHORT).show()

            chatviewModel.deleteChat(param1!!.clienteID, UserSession.getId())

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()

            val f2 = PedidosFragment()
            fragmentTransaction?.replace(R.id.root_container, f2)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

            var token = param1?.clienteID.toString()
            val notification = Notification("Pedido de marcação a: ${param1?.funcionario}",
                "horario: ${param1!!.diaHora},Foi recusado!")
            val notificacao = PushNotification(
                notification,
                "/topics/$token"
            )
            sendNotification(notificacao)

        }
        if (v!!.id == R.id.btVerMapa) {
            val f2 = MapsFragment()
            val bundle = Bundle()
            bundle.putSerializable("morada", param1?.moradaMarcacao)
            bundle.putSerializable("latitude", param1?.latitudeMarcacao)
            bundle.putSerializable("longitude",param1?.longitudeMarcacao)

            f2?.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()


            fragmentTransaction?.replace(R.id.root_container, f2)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()

        }
        if (v!!.id == R.id.showC) {
            val f2 = VistaPerfilClienteFragment()
            val bundle = Bundle()
            bundle.putSerializable("id", param1?.clienteID.toString())
            f2?.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()


            fragmentTransaction?.replace(R.id.root_container, f2)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

    }

    /**
     * Manda uma notificacao
     * @param notification notificacao a ser mandada
     */
    private fun sendNotification(notification: PushNotification) = CoroutineScope(
        Dispatchers.IO).launch {
        try {
            val retrofit = RetrofitServiceFirebase.getInstance()
            val response = retrofit.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: ${Gson().toJson(notification)}")
            } else {
                Log.e("TAG", response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }
}