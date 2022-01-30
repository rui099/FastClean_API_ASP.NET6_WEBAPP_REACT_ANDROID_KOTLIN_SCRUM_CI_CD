package com.example.fastclean.Fragments.Marcacao

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fastclean.Adapters.MarcacaoAdapter
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.R
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Chat.ChatViewModel
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModeFactory
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModel
import com.example.fastclean.ViewModels.Perfil.PerfilViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarcacaoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarcacaoFragment : Fragment() {
    lateinit var marcacaoAdapter : MarcacaoAdapter

    lateinit var contexto : Context
    private lateinit var viewModel: ListaMarcacoesViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val v1 = inflater.inflate(R.layout.fragment_marcacao, container, false)

        viewModel = ViewModelProvider(this, ListaMarcacoesViewModeFactory(MarcacoesRepository(retrofitService))).get(
            ListaMarcacoesViewModel::class.java)

        val clickDecorrer = v1.findViewById<Button>(R.id.btn_decorrer)
        val clickTerminado = v1.findViewById<Button>(R.id.btn_terminado)

        clickDecorrer.setBackgroundColor(Color.parseColor("#6395B9"))
        viewModel.marcacoesADecorrer(UserSession.getId())


        clickDecorrer.setOnClickListener {

            clickDecorrer.setBackgroundColor(Color.parseColor("#6395B9"))
            marcacaoAdapter.clearItems()
            marcacaoAdapter.notifyDataSetChanged()
            viewModel.marcacoesADecorrer(UserSession.getId())
            clickTerminado.setBackgroundColor(Color.parseColor("#4682B4"))

        }
        clickTerminado.setOnClickListener {

            clickTerminado.setBackgroundColor(Color.parseColor("#6395B9"))
            marcacaoAdapter.clearItems()
            marcacaoAdapter.notifyDataSetChanged()
            viewModel.marcacoesTerminadas(UserSession.getId())
            clickDecorrer.setBackgroundColor(Color.parseColor("#4682B4"))
        }

        viewModel.marcacaoList.observe(viewLifecycleOwner,{
            setAdapterList(it as List)
            marcacaoAdapter.notifyDataSetChanged()

        })

        this.marcacaoAdapter = MarcacaoAdapter(contexto)


        var recyclerV = v1.findViewById(R.id.recycler_view) as RecyclerView

        val llm =  LinearLayoutManager(activity)
        llm.reverseLayout = true
        llm.stackFromEnd = true
        recyclerV.layoutManager = llm
        recyclerV.adapter = this.marcacaoAdapter


        return v1

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarcacaoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MarcacaoFragment()
    }
    fun setAdapterList(list: List<Marcacao>) {
        marcacaoAdapter.addItems(list!!)
    }
}