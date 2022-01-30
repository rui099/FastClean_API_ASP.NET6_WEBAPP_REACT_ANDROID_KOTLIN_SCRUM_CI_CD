package com.example.fastclean.Fragments.Registo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.fastclean.R
import java.util.regex.Matcher
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterClientFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterClientFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

//    private var DATE_PATTERN:String = "(0?[1-9]|[12][0-9]|3[01]) [/.-] (0?[1-9]|1[012]) [/.-] ((19|20)\\d\\d)"

//    private lateinit var pattern: Pattern
//    private lateinit var matcher: Matcher

    private lateinit var name:EditText
    private lateinit var email:EditText
    private lateinit var phone:EditText
    private lateinit var birthday:EditText
    private lateinit var pass:EditText
    private lateinit var cPass:EditText
    private lateinit var address:EditText
    private lateinit var city:EditText
    private lateinit var postal:EditText

    private lateinit var btnNext:Button
    private lateinit var btnReturn:ImageButton

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
        val view = inflater.inflate(R.layout.fragment_register_client, container, false)

        name = view.findViewById(R.id.etName)
        email = view.findViewById(R.id.etEmail)
        phone = view.findViewById(R.id.etPhone)
        birthday = view.findViewById(R.id.etBirthday)
        pass = view.findViewById(R.id.etPassword)
        cPass = view.findViewById(R.id.etPasswordConfirm)
        address = view.findViewById(R.id.etAddress)
        city = view.findViewById(R.id.etCity)
        postal = view.findViewById(R.id.etPostal)

        btnNext = view.findViewById(R.id.btNext)
        btnReturn = view.findViewById(R.id.btReturn)

        btnNext.setOnClickListener(this)
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
         * @return A new instance of fragment RegisterClientFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterClientFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View) {

//        matcher = Pattern.compile(DATE_PATTERN).matcher(birthday.text.toString())

        if(v.id == R.id.btNext) {
            if(name.text.isBlank()) {
                Toast.makeText(activity, "Preencha o seu nome", Toast.LENGTH_SHORT).show()
            } else if(email.text.isBlank()) {
                Toast.makeText(activity, "Preencha o seu email", Toast.LENGTH_SHORT).show()
            //} else if(!isValidEmail(email.text.toString())) {
            //    Toast.makeText(activity, "Email invalido", Toast.LENGTH_SHORT).show()
            //    email.setText("")
            } else if(phone.text.isBlank()){
                Toast.makeText(activity, "Preencha o número de telefone", Toast.LENGTH_SHORT).show()
            } else if(birthday.text.isBlank()){
                Toast.makeText(activity, "Preencha a data de nascimento", Toast.LENGTH_SHORT).show()
//            } else if(!matcher.matches()){
//                Toast.makeText(activity, "Data de nascimento inválida", Toast.LENGTH_SHORT).show()
            } else if(pass.text.isBlank()){
                Toast.makeText(activity, "Preencha a password", Toast.LENGTH_SHORT).show()
            } else if(cPass.text.isBlank()){
                Toast.makeText(activity, "Confirme a password", Toast.LENGTH_SHORT).show()
            } else if(pass.text.toString() != cPass.text.toString()){
                Toast.makeText(activity, "Passwords diferentes", Toast.LENGTH_SHORT).show()
            } else if(address.text.isBlank()){
                Toast.makeText(activity, "Preencha a morada", Toast.LENGTH_SHORT).show()
            } else if(city.text.isBlank()){
                Toast.makeText(activity, "Preencha a cidade", Toast.LENGTH_SHORT).show()
            } else if(postal.text.isBlank()){
                Toast.makeText(activity, "Preencha o código postal", Toast.LENGTH_SHORT).show()
            } else {
                val fragment = RegisterClientFilesFragment()
                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }
        }

        if (v.id == R.id.btReturn){
            val fragment = RegisterChoiceFragment()
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }

//    private fun isValidEmail(str: String): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
//    }
//
//    private fun isValidPhone(str: String): Boolean {
//        return android.util.Patterns.PHONE.matcher(str).matches()
//    }

//    fun validate(date: String): Boolean {
//        matcher = pattern.matcher(date)
//        return if (matcher.matches()) {
//            matcher.reset()
//            if (matcher.find()) {
//                val day: String = matcher.group(1)
//                val month: String = matcher.group(2)
//                val year: Int = matcher.group(3).toInt()
//                if (day == "31" &&
//                    (month == "4" || month == "6" || month == "9" || month == "11" || month == "04" || month == "06" || month == "09")
//                ) {
//                    false // only 1,3,5,7,8,10,12 has 31 days
//                } else if (month == "2" || month == "02") {
//                    //leap year
//                    if (year % 4 == 0) {
//                        if (day == "30" || day == "31") {
//                            false
//                        } else {
//                            true
//                        }
//                    } else {
//                        if (day == "29" || day == "30" || day == "31") {
//                            false
//                        } else {
//                            true
//                        }
//                    }
//                } else {
//                    true
//                }
//            } else {
//                false
//            }
//        } else {
//            false
//        }
//    }
}
