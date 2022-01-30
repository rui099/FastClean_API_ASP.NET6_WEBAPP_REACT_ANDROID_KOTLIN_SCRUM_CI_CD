package com.example.fastclean.Fragments.Marcacao

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fastclean.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(),OnMapReadyCallback {

    private var morada: String? = null
    private var tipo: String? = null
    private var latitude: String? = null
    private var longitude: String? = null

    lateinit var funcCoords: LatLng

    lateinit var contexto: Context
    private lateinit var mMap: GoogleMap
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
        }


    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var v: View =  inflater.inflate(R.layout.fragment_maps2, container, false)

        funcCoords = LatLng(latitude!!.toDouble(), longitude!!.toDouble())

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.addMarker(MarkerOptions()
            .position(funcCoords)
            .title("Morada")
            .snippet(id.toString())
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)))
            ?.snippet = morada

        val zoomLevel = 20.0f
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(funcCoords,zoomLevel))
    }
}