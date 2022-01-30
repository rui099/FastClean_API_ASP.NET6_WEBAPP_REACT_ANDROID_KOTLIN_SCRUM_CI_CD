package com.example.fastclean.Fragments.Perfil

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.MainActivity
import com.example.fastclean.R
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Perfil.PerfilViewModel
import com.example.fastclean.ViewModels.Perfil.PerfilViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DefinicoesFuncionarioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DefinicoesFuncionarioFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var btnEditar_user: Button
    private lateinit var btnLogout: Button
    private lateinit var btnReturn: Button
    private lateinit var viewModel: PerfilViewModel
    private val retrofitService = RetrofitService.getInstance()

    lateinit var contexto : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_definicoes_funcionario, container, false)
        viewModel = ViewModelProvider(this, PerfilViewModelFactory(UtilizadoresRepository(retrofitService), ReviewClienteRepository(retrofitService), ReviewFuncionarioRepository(retrofitService))).get(
            PerfilViewModel::class.java)

        btnLogout = view.findViewById(R.id.bt_logout)
        btnEditar_user = view.findViewById(R.id.bt_editar_user)
        btnReturn = view.findViewById(R.id.btReturn)

        btnLogout.setOnClickListener(this)
        btnEditar_user.setOnClickListener(this)
        btnReturn.setOnClickListener(this)


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DefinicoesFuncionarioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DefinicoesFuncionarioFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.bt_logout){
            if(UserSession.getRole().equals("Funcionario")){
                var mainActivity = context as MainActivity
                mainActivity.logout()

                viewModel.updateEstadoIndisponivelCliente(UserSession.getId())
            }


            context?.let { UserSession.cleanSession(it) }
            activity?.finish()
        }

        if (v.id == R.id.bt_editar_user){
            val bundle = Bundle()
            bundle.putString("role", "worker")
            val fragment = ProfileEditFragment()
            fragment.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        if (v.id == R.id.btReturn){
            val fragment = WorkerProfileFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }

    interface PerfilComunication{
        fun logout()
    }
}