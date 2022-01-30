package com.example.fastclean.Fragments.Marcar

import android.app.Activity
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.fastclean.Fragments.Perfil.ClientProfileFragment
import com.example.fastclean.Fragments.Perfil.WorkerProfileFragment
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.R
import com.example.fastclean.Repositories.ReviewClienteRepository
import com.example.fastclean.Repositories.ReviewFuncionarioRepository
import com.example.fastclean.Repositories.UtilizadoresRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Perfil.PerfilViewModel
import com.example.fastclean.ViewModels.Perfil.PerfilViewModelFactory
import com.google.android.gms.common.api.Api
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.awaitAll
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EscolherTipoLimpezaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EscolherTipoLimpezaFragment : Fragment(),View.OnClickListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var proximo: Button
    lateinit var escolherMorada: Switch

    lateinit var rua: TextView
    lateinit var numero: TextView
    lateinit var codP: TextView
    lateinit var freguesia: TextView
    lateinit var concelho: TextView
    lateinit var distrito: TextView
    lateinit var radioGroup : RadioGroup
    lateinit var place : Morada


    private lateinit var viewModel: PerfilViewModel


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
        val view = inflater.inflate(R.layout.fragment_escolher_tipo_limpeza, container, false)

        escolherMorada = view.findViewById(R.id.escolherMorada)
        proximo = view.findViewById(R.id.proximo)

        rua = view.findViewById(R.id.rua)
        numero = view.findViewById(R.id.numero)
        freguesia = view.findViewById(R.id.freguesia)
        codP = view.findViewById(R.id.codPostal)
        concelho = view.findViewById(R.id.concelho)
        distrito = view.findViewById(R.id.distrito)
        radioGroup = view.findViewById(R.id.radioGroup)

        escolherMorada.setOnClickListener(this)
        proximo.setOnClickListener(this)

        var retrofitService =RetrofitService.getInstance()

        viewModel = ViewModelProvider(this, PerfilViewModelFactory(UtilizadoresRepository(retrofitService),
            ReviewClienteRepository(retrofitService), ReviewFuncionarioRepository(retrofitService))).get(
            PerfilViewModel::class.java)

        viewModel.getCliente(UserSession.getId())

        viewModel.cliente.observe(viewLifecycleOwner, {
            place = it.morada

        })


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EscolherTipoLimpezaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            EscolherTipoLimpezaFragment()
            }




    override fun onClick(v: View) {

        if (v.id == R.id.escolherMorada) {
            Log.e("frag", "escolherMorada")
            rua.isEnabled = escolherMorada.isChecked
            numero.isEnabled = escolherMorada.isChecked
            codP.isEnabled = escolherMorada.isChecked
            freguesia.isEnabled = escolherMorada.isChecked
            concelho.isEnabled = escolherMorada.isChecked
            distrito.isEnabled = escolherMorada.isChecked
        }

        if (v.id == R.id.proximo) {
            var morada: String? =null
            var coords: LatLng? =null
            var radioButton : RadioButton? =null

            val selectedId: Int = radioGroup.checkedRadioButtonId
            if(selectedId == -1)
                Toast.makeText(activity, "Escolha o tipo de limpeza", Toast.LENGTH_SHORT)
                    .show()
            else
            radioButton = radioGroup.findViewById(selectedId)


            if (escolherMorada.isChecked) {
                if (rua.text.isBlank() || numero.text.isBlank() || codP.text.isBlank()
                    || distrito.text.isBlank()
                ) {

                } else {
                    var ruat = rua.text.toString()
                    var numerot = numero.text.toString()
                    var codPt = codP.text.toString()
                    var freguesiat = freguesia.text.toString()
                    var concelhot = concelho.text.toString()
                    var distritot = distrito.text.toString()
                    morada = "$ruat $numerot $codPt $freguesiat $concelhot $distritot"
                    coords = getCoords(morada)
                    Log.e("self", coords.toString())
                    Log.e("morada", morada)
                }

            }else if(escolherMorada.isChecked == false){

                morada = place.rua + " " + place.numero +
                        " " + place.codigoPostal + " " + place.freguesia+
                        " " + place.concelho+ " " + place.distrito
                coords = getCoords(morada!!)
                Log.e("NOTmorada", morada)
            }

            if(morada != null && coords != null && selectedId != -1) {

                var f2 = MapsFragment()
                val bundle = Bundle()
                bundle.putSerializable("morada", morada)
                bundle.putSerializable("latitude", coords.latitude.toString())
                bundle.putSerializable("longitude", coords.longitude.toString())
                bundle.putSerializable("tipo", radioButton!!.text.toString())
                f2.arguments = bundle

                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.root_container, f2)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
            }else
                Toast.makeText(
                    activity,
                    "Algo correu mal na introdção da morada",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    /**
     * trnasforms the user address into coordinates
     * @param morada user's adress
     * @return coordinates
     */
    fun getCoords(morada : String): LatLng? {
        var lat : Double? = null
        var lng : Double? = null
        var response: String


        val thread = Thread {
            try {
                val url =
                    //Substituir o URL pelo que está em comentario
                    //URL("https://")
                    URL("https://maps.googleapis.com/maps/api/geocode/json?address=$morada&key=AIzaSyAw49yLOH8yb0KL9VfcdbjxVFSQgougDBQ")
                Log.v("urldirection", url.toString())

                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"

                val inp = BufferedInputStream(conn.inputStream)
                response = org.apache.commons.io.IOUtils.toString(inp, "UTF-8")
                val jsonObject = JSONObject(response)
                //Log.e("AAA", "code: "+jsonObject)

                val array = jsonObject.getJSONArray("results")
                val routes = array.getJSONObject(0)
                val geometry = routes.getJSONObject("geometry")
                val location = geometry.getJSONObject("location")
                //Log.e("AAA", "code: "+legs)


                lat = location.getDouble("lat")
                lng = location.getDouble("lng")



            } catch (e: JSONException) {
                Log.e(ContentValues.TAG, e.toString() )
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, e.toString() )
            }
        }

        thread.start()
        thread.join()

        //Substituir o URL pelo que está em comentario
        return lat?.let { lng?.let { it1 -> LatLng(it, it1) } }
        //return LatLng(41.183792, -8.153267)
    }


}

