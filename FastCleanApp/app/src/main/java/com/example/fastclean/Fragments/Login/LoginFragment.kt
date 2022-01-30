package com.example.fastclean.Fragments.Login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Fragments.Registo.RegisterChoiceFragment
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Login.Login
import com.example.fastclean.R
import com.example.fastclean.Repositories.LoginRepository
import com.example.fastclean.Repositories.SubRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.LoginValidator.Companion.isValidEmail
import com.example.fastclean.Utils.LoginValidator.Companion.isValidPassword
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Login.LoginViewModel
import com.example.fastclean.ViewModels.Login.LoginViewModelFactory
import com.example.fastclean.ViewModels.Perfil.SubViewModel
import com.example.fastclean.ViewModels.Perfil.SubViewModelFactory
import android.content.SharedPreferences

import android.content.Context.MODE_PRIVATE
import android.content.Context.MODE_PRIVATE
import android.content.Context.MODE_PRIVATE
import android.content.Context.MODE_PRIVATE













// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class LoginFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var viewModel: LoginViewModel
    private lateinit var subViewModel : SubViewModel
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
        // Inflate the layout for this fragment

        subViewModel =
            ViewModelProvider(this, SubViewModelFactory(SubRepository(retrofitService))).get(
                SubViewModel::class.java
            )

        val sharedPref = activity?.getSharedPreferences("Login", MODE_PRIVATE)

        if (sharedPref != null) {
            if(sharedPref.getString("id",null) != null) {
                UserSession.setToken(sharedPref.getString("token", null)!!)
                sharedPref.getString("id", null)?.let { UserSession.setId(it.toInt()) }
                sharedPref.getString("cargo", null)?.let { UserSession.setRole(it) }
                sharedPref.getString("nome", null)?.let { UserSession.setName(it) }

                if (sharedPref.getString("cargo",null) == "Funcionario") {
                    subViewModel.putNullSub(sharedPref.getString("id", null)!!.toInt())
                }
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)

            }
        }
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        btnLogin = view.findViewById(R.id.bt_login)
        btnRegister = view.findViewById(R.id.bt_registar)
        etEmail = view.findViewById(R.id.email)
        etPassword = view.findViewById(R.id.password)

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)


        viewModel =
            ViewModelProvider(this, LoginViewModelFactory(LoginRepository(retrofitService))).get(
                LoginViewModel::class.java
            )


        loginToken()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.bt_login){

            var email: String = etEmail.text.toString()
            var password: String = etPassword.text.toString()
            var emailValid = true
            var passwordValid = true

            if (!isValidEmail(etEmail.text.toString())) {
                etEmail.error = "Email invalido"
                etEmail.requestFocus()
                emailValid = false
            }else
                if (!isValidPassword(etPassword.text.toString())) {
                    etPassword.error = "Password invalida"
                    if(emailValid)
                        etPassword.requestFocus()
                    passwordValid = false

                }

            if(emailValid && passwordValid) {
                val login = Login(email,password)

                viewModel.login(login)

            }
        }

        if (v.id == R.id.bt_registar){
            val fragment = RegisterChoiceFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }
    /**
     * get the login token and some adicional information from the login
     */
    fun loginToken(){
        viewModel.userInfo.observe(viewLifecycleOwner, Observer {
            val intent = Intent(context, MainActivity::class.java)
            UserSession.setToken(it.token)
            UserSession.setId(it.id)
            UserSession.setRole(it.cargo)
            UserSession.setName(it.nome)

            val sharedPref = activity?.getSharedPreferences("Login", MODE_PRIVATE)

            val Ed = sharedPref?.edit()
            Ed?.putString("token", it.token)
            Ed?.putString("id", it.id.toString())
            Ed?.putString("cargo",it.cargo)
            Ed?.putString("nome", it.nome)
            Ed?.commit()

            if (it.cargo == "Funcionario") {
                subViewModel.putNullSub(it.id)
            }
            Toast.makeText(activity, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            activity?.finish()
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }
}