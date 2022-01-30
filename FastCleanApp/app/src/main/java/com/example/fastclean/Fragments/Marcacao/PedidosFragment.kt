package com.example.fastclean.Fragments.Marcacao

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.fastclean.Adapters.PedidoAdapter
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.R
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModeFactory
import com.example.fastclean.ViewModels.ListaDeMarcacoes.ListaMarcacoesViewModel
import com.example.fastclean.ViewModels.ListaDePedidos.ListaPedidosViewModel
import com.example.fastclean.ViewModels.ListaDePedidos.ListaPedidosViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PedidosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PedidosFragment : Fragment() {
    lateinit var pedidosAdapter : PedidoAdapter

    lateinit var contexto : Context
    private lateinit var viewModel: ListaPedidosViewModel
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
        val v1 = inflater.inflate(R.layout.fragment_pedidos, container, false)

        viewModel = ViewModelProvider(this, ListaPedidosViewModelFactory(MarcacoesRepository(retrofitService))).get(
            ListaPedidosViewModel::class.java)

        viewModel.getPedidos(UserSession.getId())

        viewModel.pedidosList.observe(viewLifecycleOwner,{
            setAdapterList(it as List)
            pedidosAdapter.notifyDataSetChanged()

        })

        this.pedidosAdapter = PedidoAdapter(contexto)


        var recyclerV = v1.findViewById(R.id.recycler_view) as RecyclerView
        recyclerV.layoutManager = LinearLayoutManager(activity)
        recyclerV.adapter = this.pedidosAdapter


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
            PedidosFragment()
    }
    fun setAdapterList(list: List<Marcacao>) {

        pedidosAdapter.addItems(list!!)
    }


}