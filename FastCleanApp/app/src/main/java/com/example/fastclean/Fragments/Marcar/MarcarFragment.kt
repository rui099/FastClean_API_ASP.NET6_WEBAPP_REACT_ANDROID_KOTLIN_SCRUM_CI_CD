package com.example.fastclean.Fragments.Marcar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.fastclean.Fragments.Registo.RegisterWorkerFragment
import com.example.fastclean.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MarcarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarcarFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters


    lateinit var agora: Button
    lateinit var agendar: Button

    private var morada: String? = null
    private var tipo: String? = null
    private var latitude: String? = null
    private var longitude: String? = null
    private var funcId : String?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            morada = it.getSerializable("morada") as String?
            latitude = it.getSerializable("latitude") as String?
            longitude = it.getSerializable("longitude") as String?
            tipo = it.getSerializable("tipo") as String?
            funcId= it.getSerializable("funcId") as String?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_marcar, container, false)

        agora = v.findViewById(R.id.btAgora)
        agendar = v.findViewById(R.id.btAgendar)

        agendar.setOnClickListener(this)
        agora.setOnClickListener(this)

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarcarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            MarcarFragment()
    }

    override fun onClick(v: View) {
        if(v.id == R.id.btAgendar) {
            sendBundleMarcacao("Agendar_Hora")
        }

        if(v.id == R.id.btAgora) {

            sendBundleMarcacao("Agora")

        }
    }

    fun sendBundleMarcacao(tipoA : String){
        var f2 : Fragment? = null

        if(tipoA == "Agendar_Hora")
            f2 = MarcarHoraFragment()
        else if(tipoA == "Agora")
            f2 = MarcarAgoraFragment()

        val bundle = Bundle()
        bundle.putSerializable("morada", morada)
        bundle.putSerializable("latitude", latitude)
        bundle.putSerializable("longitude",longitude)
        bundle.putSerializable("tipo", tipo)
        bundle.putSerializable("tipoA", tipoA)
        bundle.putSerializable("funcId", funcId)
        f2?.arguments = bundle

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.root_container, f2!!)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
}