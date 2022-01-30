package com.example.fastclean.Fragments.Marcar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fastclean.R
import android.util.Log


import java.util.Calendar;
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Chat.NewChat
import com.example.fastclean.Models.Firebase.Notification
import com.example.fastclean.Models.Firebase.PushNotification


import com.example.fastclean.Models.Marcacao.MarcacaoPost
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.Repositories.LoginRepository
import com.example.fastclean.Repositories.MarcacoesRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.RestService.RetrofitServiceFirebase
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Chat.ChatViewModel
import com.example.fastclean.ViewModels.Chat.ChatViewModelFactory
import com.example.fastclean.ViewModels.Login.LoginViewModel
import com.example.fastclean.ViewModels.Login.LoginViewModelFactory
import com.example.fastclean.ViewModels.Marcar.MarcarViewModel
import com.example.fastclean.ViewModels.Marcar.MarcarViewModelFactory
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
 * Use the [MarcarHoraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarcarHoraFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var morada: String? = null
    private var tipo: String? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private var funcId : String?= null
    private var tipoAgendamento : String?= null



    lateinit var hora: EditText
    lateinit var data: EditText
    lateinit var agendar: Button
    lateinit var imovel: RadioGroup
    lateinit var numQuartos : EditText
    lateinit var numRetretes : EditText
    lateinit var cozinha : CheckBox
    lateinit var sala : CheckBox
    lateinit var hPrevistas : EditText
    lateinit var descricao : EditText
    lateinit var viewModel : MarcarViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var chatviewModel: ChatViewModel
    var statusT = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            morada = it.getSerializable("morada") as String?
            latitude = it.getSerializable("latitude") as String?
            longitude = it.getSerializable("longitude") as String?
            tipo = it.getSerializable("tipo") as String?
            funcId= it.getSerializable("funcId") as String?
            tipoAgendamento = it.getSerializable("tipoA") as String?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_marcar_hora, container, false)

        hora = v.findViewById(R.id.etHora)
        data = v.findViewById(R.id.etData)
        agendar = v.findViewById(R.id.btAgendar)
        imovel = v.findViewById(R.id.TipoImovel)
        numQuartos = v.findViewById(R.id.NumQuartosBanho)
        numRetretes = v.findViewById(R.id.NumQuartos)
        cozinha  = v.findViewById(R.id.cbCozinha)
        sala   = v.findViewById(R.id.cbSala)
        hPrevistas = v.findViewById(R.id.hPrevistas)
        descricao = v.findViewById(R.id.etDesc)

        hora.inputType = InputType.TYPE_NULL
        data.inputType = InputType.TYPE_NULL

        hora.setOnClickListener(this)
        data.setOnClickListener(this)
        agendar.setOnClickListener(this)

        chatviewModel = ViewModelProvider(this, ChatViewModelFactory(ChatRepository(retrofitService))).get(
            ChatViewModel::class.java)

        viewModel =
            ViewModelProvider(this, MarcarViewModelFactory(MarcacoesRepository(retrofitService))).get(
                MarcarViewModel::class.java
            )

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarcarHoraFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarcarHoraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View) {
        if (v.id == R.id.etHora) {
            val cldr: Calendar = Calendar.getInstance()
            var hour = cldr.get(Calendar.HOUR_OF_DAY)
            var minutes = cldr.get(Calendar.MINUTE)

            var picker = TimePickerDialog(
                this.context,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                { tp, sHour, sMinute -> hora.setText("${checkDigit(sHour)}:${checkDigit(sMinute)}") }, hour, minutes, true
            )
            picker.show()
        }

        if (v.id == R.id.etData) {
            val cldr: Calendar = Calendar.getInstance()
            var day = cldr.get(Calendar.DAY_OF_MONTH)
            var month = cldr.get(Calendar.MONTH)
            var year = cldr.get(Calendar.YEAR)

            var picker = this.context?.let {
                DatePickerDialog(
                    it,
                    { dp, sYear, sMonth, sDay -> data.setText("${checkDigit(sDay)}/${checkDigit(sMonth+1)}/${checkDigit(sYear)}") }, year, month, day,
                )
            }
            picker?.show()
        }

        if(v.id == R.id.btAgendar) {
            var numQuartosT = "0"
            var numRetretesT = "0"
            var salaT = false
            var cozinhaT = false

            if (numQuartos.text.toString() != "")
                numQuartosT = numQuartos.text.toString()
            if(numRetretes.text.toString() != "")
                numRetretesT = numRetretes.text.toString()
            if(sala.isChecked )
                salaT = true
            if(cozinha.isChecked )
                cozinhaT = true


            if (imovel.checkedRadioButtonId == -1) {
                Toast.makeText(activity, "Escolha um tipo de imóvel", Toast.LENGTH_SHORT).show()
            }else if(hora.text == null){
                Toast.makeText(activity, "Escolha uma hora", Toast.LENGTH_SHORT).show()
            }else if(data.text == null){
                Toast.makeText(activity, "Escolha uma data", Toast.LENGTH_SHORT).show()
            }else if(hPrevistas.text.toString() == ""){
                Toast.makeText(activity, "Ensira um previsão de tempo", Toast.LENGTH_SHORT).show()
            } else {
                var imovelCh = imovel.findViewById<RadioButton>(imovel.checkedRadioButtonId)
                var diaHora = data.text.toString() + " " + hora.text.toString()

                var marcacao = MarcacaoPost(
                    imovelCh.text.toString(),
                    tipo!!,tipoAgendamento!!,
                    numQuartosT,
                    descricao.text.toString(),
                    diaHora,
                    numRetretesT,
                    cozinhaT,
                    salaT,
                    UserSession.getId(),
                    funcId,
                    morada!!,
                    latitude,
                    longitude,
                    hPrevistas.text.toString())

                Log.e("THREAD", marcacao.toString())

                chatviewModel.postChat(NewChat(UserSession.getId(), funcId!!.toInt()))

                viewModel.postMarcacaoDepois(marcacao)
                viewModel.status.observe(viewLifecycleOwner, Observer {
                    if(it == true){
                        Toast.makeText(activity, "Sucesso", Toast.LENGTH_SHORT).show()
                        val fragment = MarcarSucessoFragment()
                        val fragmentManager = activity?.supportFragmentManager
                        val fragmentTransaction = fragmentManager?.beginTransaction()
                        fragmentTransaction?.replace(R.id.root_container, fragment)
                        fragmentTransaction?.addToBackStack(null)
                        fragmentTransaction?.commit()

                        var token = funcId.toString()
                        val notification = Notification("Pedido de marcação de: ${UserSession.getName()}",
                            "imovel: ${imovelCh.text.toString()} horario: $diaHora")
                        val notificacao = PushNotification(
                            notification,
                            "/topics/$token"
                        )
                        sendNotification(notificacao)
                    }
                })

            }

        }
    }

    fun checkDigit(i:Int): String {
        return if (i <= 9) "0$i" else java.lang.String.valueOf(i)
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
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