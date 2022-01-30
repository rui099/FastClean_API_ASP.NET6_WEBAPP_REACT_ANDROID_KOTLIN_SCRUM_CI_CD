package com.example.fastclean.Fragments.Perfil

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.PasswordDTO
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
 * Use the [ProfileEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileEditFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var viewModel: PerfilViewModel



    lateinit var etOldPass: EditText
    lateinit var etPass: EditText
    lateinit var etCPass: EditText

    private lateinit var rua:EditText
    private lateinit var numero:EditText
    private lateinit var codigoPostal:EditText
    private lateinit var freguesia:EditText
    private lateinit var concelho:EditText
    private lateinit var distrito:EditText


    private lateinit var contacto:EditText


    lateinit var btnGuardarPass: Button
    lateinit var btnGuardarMorada: Button
    lateinit var btnGuardarContacto: Button
    lateinit var btnReturn: ImageButton

    private val retrofitService = RetrofitService.getInstance()
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_edit, container, false)

//        etDesc = view.findViewById(R.id.etDesc)
        etOldPass = view.findViewById(R.id.etOldPassword)
        etPass = view.findViewById(R.id.etPassword)
        etCPass = view.findViewById(R.id.etPasswordConfirm)


        rua = view.findViewById(R.id.etRua)
        numero = view.findViewById(R.id.etNumero)
        codigoPostal = view.findViewById(R.id.etPostal)
        freguesia = view.findViewById(R.id.etFreguesia)
        concelho = view.findViewById(R.id.etConcelho)
        distrito = view.findViewById(R.id.etDistrito)

        contacto = view.findViewById(R.id.etContacto)




        btnGuardarPass = view.findViewById(R.id.btGuardarPass)
        btnGuardarMorada = view.findViewById(R.id.btGuardarMorada)
        btnGuardarContacto = view.findViewById(R.id.btGuardarContacto)
        btnReturn = view.findViewById(R.id.btReturn)



        btnGuardarPass.setOnClickListener(this)
        btnGuardarMorada.setOnClickListener(this)
        btnGuardarContacto.setOnClickListener(this)
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
         * @return A new instance of fragment ProfileEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View) {


        if(v.id == R.id.btGuardarPass){


            if(etOldPass.text.isBlank()){
                Toast.makeText(activity, "Preencha a password", Toast.LENGTH_SHORT).show()
            }else if(etPass.text.isBlank()){
                Toast.makeText(activity, "Preencha a nova password", Toast.LENGTH_SHORT).show()
            } else if(etCPass.text.isBlank()){
                Toast.makeText(activity, "Confirme a nova password", Toast.LENGTH_SHORT).show()
            } else if(etPass.text.toString() != etCPass.text.toString()){
                Toast.makeText(activity, "Novas Passwords diferentes", Toast.LENGTH_SHORT).show()
            } else{

                var pass = PasswordDTO(
                    etOldPass.text.toString(),
                    etPass.text.toString(),
                    etCPass.text.toString()
                )

                viewModel.atualizarPassword(UserSession.getId(), pass)
                viewModel.status.observe(viewLifecycleOwner,{
                    if(it==true){
                        Toast.makeText(activity, "Password alterada", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Verifique a password", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        if(v.id == R.id.btGuardarMorada){
            if(rua.text.isBlank()){
                Toast.makeText(activity, "Preencha a Rua", Toast.LENGTH_SHORT).show()
            } else if(numero.text.isBlank()){
                Toast.makeText(activity, "Preencha o Numero", Toast.LENGTH_SHORT).show()
            } else if(codigoPostal.text.isBlank()){
                Toast.makeText(activity, "Preencha o código postal", Toast.LENGTH_SHORT).show()
            } else if(freguesia.text.isBlank()){
                Toast.makeText(activity, "Preencha a Freguesia", Toast.LENGTH_SHORT).show()
            }else if(concelho.text.isBlank()){
                Toast.makeText(activity, "Preencha o Concelho", Toast.LENGTH_SHORT).show()
            }else if(distrito.text.isBlank()){
                Toast.makeText(activity, "Preencha o Distrito", Toast.LENGTH_SHORT).show()
            }else {

                var morada = Morada(
                    rua.text.toString(),
                    numero.text.toString().toInt(),
                    codigoPostal.text.toString(),
                    freguesia.text.toString(),
                    concelho.text.toString(),
                    distrito.text.toString()
                )
                viewModel.atualizarMorada(UserSession.getId(), morada)
                viewModel.status.observe(viewLifecycleOwner,{
                    if(it==true){
                        Toast.makeText(activity, "Morada alterada", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Verifique a Morada", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        if(v.id == R.id.btGuardarContacto){
            if(contacto.text.isBlank()){
                Toast.makeText(activity, "Preencha o Contacto", Toast.LENGTH_SHORT).show()
            }else {
                viewModel.atualizarContacto(UserSession.getId(), contacto.text.toString().toInt())
                viewModel.status.observe(viewLifecycleOwner,{
                    if(it==true){
                        Toast.makeText(activity, "Contacto alterada", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Verifique a contacto", Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }

        if(v.id == R.id.btReturn){
            val args = this.arguments
            val role = args?.get("role")

            if(role.toString() == "client") {
                val fragment = DefinicoesClienteFragment()
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.root_container, fragment)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            } else if(role.toString() == "worker") {
                val fragment = DefinicoesFuncionarioFragment()
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.root_container, fragment)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }
        }


    }
}