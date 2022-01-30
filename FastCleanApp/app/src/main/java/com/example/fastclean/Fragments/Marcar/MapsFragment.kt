package com.example.fastclean.Fragments.Marcar

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import androidx.appcompat.app.AlertDialog

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.tiagoaguiar.tutorial.gmaps.MarkerAdapter
import com.example.fastclean.Fragments.Perfil.VistaPerfilFuncFragment

import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioDetails
import com.example.fastclean.R
import com.example.fastclean.Repositories.*
import com.example.fastclean.RestService.RetrofitService

import com.example.fastclean.ViewModels.Mapa.MapaViewModel
import com.example.fastclean.ViewModels.Mapa.MapaViewModelFactory


import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll

class MapsFragment : Fragment() ,OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private var morada: String? = null
    private var tipo: String? = null
    private var latitude: String? = null
    private var longitude: String? = null

    private lateinit var mMap: GoogleMap
    var lista : List<FuncionarioDetails> = listOf()
    lateinit var contexto: Context
    lateinit var cliente: LatLng
    lateinit var viewModel: MapaViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            morada = it.getSerializable("morada") as String?
            latitude = it.getSerializable("latitude") as String?
            longitude = it.getSerializable("longitude") as String?
            tipo = it.getSerializable("tipo") as String?
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v: View = inflater.inflate(R.layout.fragment_maps, container, false)
        cliente = LatLng(latitude!!.toDouble(), longitude!!.toDouble())


        getLista()


        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

    }

    companion object {

        fun newInstance() = MapsFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        var id = 100
        mMap = googleMap

        mMap.setInfoWindowAdapter(MarkerAdapter(contexto,cliente))
        mMap.setOnInfoWindowClickListener(this)
        //val cliente = LatLng(41.183024, -8.154143)
        val zoomLevel = 15.0f //This goes up to 21

        mMap.addMarker(MarkerOptions()
            .position(cliente)
            .title("Cliente")
            .snippet(id.toString())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
            ?.snippet = "Está aqui."



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cliente,zoomLevel))

        addMarkers(mMap)
    }

    /**
     * Adds markers to google maps
     * @param googleMap google map
     */
    private fun addMarkers(googleMap: GoogleMap) {

        lista.forEach { place ->
            val coords = LatLng(place.latitude, place.longitude)

            val marker = googleMap.addMarker(MarkerOptions()
                .title(place.id.toString())
                .position(coords)
            )

            Log.e("THREAD",coords.toString())
            if (marker != null) {
                marker.tag = place
            }

        }



    }



    override fun onInfoWindowClick(p0: Marker) {

        if (p0?.snippet != "Está aqui.") {
            var popup = LayoutInflater.from(contexto).inflate(R.layout.popup, null, false)
            val alertBuilder = AlertDialog.Builder(contexto)
            alertBuilder.setView(popup)
            var alert = alertBuilder.create()
            var marcar = popup.findViewById<Button>(R.id.marcar)
            var perfil = popup.findViewById<Button>(R.id.perfil)
            alert.show()

            marcar.setOnClickListener {
                val f2 = MarcarFragment()
                val bundle = Bundle()
                bundle.putSerializable("morada", morada)
                bundle.putSerializable("latitude", latitude)
                bundle.putSerializable("longitude", longitude)
                bundle.putSerializable("tipo", tipo)
                bundle.putSerializable("funcId", p0.title)
                f2.arguments = bundle

                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.root_container, f2)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
                alert.dismiss()
            }

            perfil.setOnClickListener {
                val f2 = VistaPerfilFuncFragment()
                val bundle = Bundle()
                bundle.putSerializable("id", p0.title)
                f2.arguments = bundle

                val fragmentManager = activity?.supportFragmentManager
                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.root_container, f2)
                fragmentTransaction?.addToBackStack(null)
                fragmentTransaction?.commit()
                alert.dismiss()
            }



            //Toast.makeText(this, marker.snippet, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Returns the list of workers
     */
    private fun getLista(){
        var retrofitService = RetrofitService.getInstance()

        viewModel =
            ViewModelProvider(this, MapaViewModelFactory(MapaRepository(retrofitService))).get(
                MapaViewModel::class.java
            )

        if(tipo == "Profissional")
            viewModel.getFuncRangeProfissional(latitude!!.toDouble(), longitude!!.toDouble(), 30)
        if(tipo == "Normal")
            viewModel.getFuncRangePNormal(latitude!!.toDouble(), longitude!!.toDouble(), 30)

        viewModel.lista.observe(viewLifecycleOwner, Observer {
            lista = it

        })

    }

}