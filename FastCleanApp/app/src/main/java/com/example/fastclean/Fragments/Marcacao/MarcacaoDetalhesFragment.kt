package com.example.fastclean.Fragments.Marcacao

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fastclean.Fragments.Registo.RegisterClientFragment
import com.example.fastclean.Fragments.Review.ReviewFragment
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.R
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Chat.ChatViewModel
import com.example.fastclean.ViewModels.Chat.ChatViewModelFactory
import com.example.fastclean.ViewModels.DetalhesDeMarcacao.DetalhesViewModel
import com.example.fastclean.ViewModels.DetalhesDeMarcacao.DetalhesViewModelFactory
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModeFactory
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarcacaoDetalhesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarcacaoDetalhesFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: Marcacao? = null

    private lateinit var btnTerminar : Button
    private lateinit var btnComecar : Button
    private lateinit var btnAvaliar : Button

    private lateinit var viewModel: DetalhesViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var chatviewModel: ChatViewModel
    private lateinit var profilePicFunc : ImageView
    private lateinit var profilePicCliente : ImageView

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
        val view = inflater.inflate(R.layout.fragment_marcacao_detalhes, container, false)

        viewModel = ViewModelProvider(this, DetalhesViewModelFactory(MarcacoesRepository(retrofitService))).get(
            DetalhesViewModel::class.java)

        profilePicFunc = view.findViewById(R.id.FuncP)
        profilePicCliente = view.findViewById(R.id.ClienteP)
        btnComecar = view.findViewById(R.id.btComecar)
        btnTerminar = view.findViewById(R.id.btTerminar)
        btnAvaliar = view.findViewById(R.id.btAvaliar)



        btnComecar.setOnClickListener(this)
        btnTerminar.setOnClickListener(this)
        btnAvaliar.setOnClickListener(this)

        if (param1?.horaInicio == null) {
            btnComecar.setBackgroundColor(Color.CYAN)
        } else {
            btnComecar.setBackgroundColor(Color.GRAY)
            btnComecar.isEnabled = false
            btnTerminar.setBackgroundColor(Color.CYAN)
            btnTerminar.isEnabled = true
        }

        if (param1?.terminada == true) {
            btnTerminar.isEnabled = false
            btnTerminar.setBackgroundColor(Color.GRAY)
            btnAvaliar.isEnabled = true
            btnAvaliar.setBackgroundColor(Color.CYAN)
        }

        if (UserSession.getRole() == "Cliente") {
            btnComecar.visibility = View.GONE
            btnTerminar.visibility = View.GONE
            if (param1?.reviewFuncionario == true) {
                btnAvaliar.isEnabled = false
                btnAvaliar.setBackgroundColor(Color.GRAY)
            }
        } else {
            if (param1?.reviewCliente == true) {
                btnAvaliar.isEnabled = false
                btnAvaliar.setBackgroundColor(Color.GRAY)
            }
            if(param1?.estadoFuncionario == "Ocupado"){
                btnComecar.isEnabled = false
                btnComecar.setBackgroundColor(Color.GRAY)
            }
        }



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
        view.findViewById<TextView>(R.id.cliente).text = param1?.cliente
        if(param1?.horaFinal == null)
            view.findViewById<TextView>(R.id.fim).text = ""
        else
            view.findViewById<TextView>(R.id.fim).text = param1?.horaFinal.toString()

        if(param1?.horaInicio == null)
            view.findViewById<TextView>(R.id.inicio).text = ""
        else
            view.findViewById<TextView>(R.id.inicio).text = param1?.horaInicio.toString()

        if(param1?.total == null)
            view.findViewById<TextView>(R.id.inicio).text = ""
        else
            view.findViewById<TextView>(R.id.Preço).text= param1?.total.toString()
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
        if (v!!.id == R.id.btTerminar) {
            viewModel.TerminarMarcacao(param1!!.marcacaoId)

            chatviewModel.deleteChat(param1!!.clienteID, UserSession.getId())

            terminada()
        }

        if (v!!.id == R.id.btAvaliar) {
            val fragment = ReviewFragment()
            val bundle = Bundle()
            if (UserSession.getRole() == "Cliente"){
                bundle.putInt("reviewed", param1!!.funcionarioID)
            } else {
                bundle.putInt("reviewed", param1!!.clienteID)
            }
            bundle.putSerializable("marcacao", param1)
            fragment.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        if (v!!.id == R.id.btComecar) {

            viewModel.iniciarMarcacao(param1!!.marcacaoId)
            comecar()
        }

    }
    /**
     * enable the Terminar button
     */
    fun comecar() {
        btnComecar.setBackgroundColor(Color.GRAY)
        btnComecar.isEnabled = false
        btnTerminar.setBackgroundColor(Color.CYAN)
        btnTerminar.isEnabled = true
    }

    /**
     * enable the Avaliar button
     */
    fun terminada() {
        btnAvaliar.isEnabled = true
        btnAvaliar.setBackgroundColor(Color.CYAN)
        btnTerminar.isEnabled = false
        btnTerminar.setBackgroundColor(Color.GRAY)

    }
}