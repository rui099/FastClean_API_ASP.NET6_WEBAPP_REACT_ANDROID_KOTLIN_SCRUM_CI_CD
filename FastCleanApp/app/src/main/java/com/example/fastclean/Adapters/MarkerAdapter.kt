package co.tiagoaguiar.tutorial.gmaps

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RatingBar
import android.widget.TextView


import com.example.fastclean.Fragments.Marcar.mapadummy.Func
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioDetails
import com.example.fastclean.R

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.json.JSONException

import org.json.JSONObject

import java.io.BufferedInputStream
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit



class MarkerAdapter(private val context: Context,var dist : LatLng) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(marker: Marker): View? = null

    override fun getInfoContents(marker: Marker): View? {
        val place = marker.tag as? FuncionarioDetails ?: return null
        marker.snippet = place.id.toString()


        var tempoWalking = getTime(dist.latitude,
            dist.longitude,
            marker.position.latitude,
            marker.position.longitude,"walking"
        )//?.toFloat()?.toBigDecimal()?.setScale(2, RoundingMode.HALF_EVEN)

        var tempoCar = getTime(dist.latitude,
            dist.longitude,
            marker.position.latitude,
            marker.position.longitude,"driving"
        )//?.toFloat()?.div(60)?.toBigDecimal()?.setScale(2, RoundingMode.HALF_EVEN)

        var tempoBike = getTime(dist.latitude,
            dist.longitude,
            marker.position.latitude,
            marker.position.longitude,"cycling"
        )//?.toFloat()?.toBigDecimal()?.setScale(2, RoundingMode.HALF_EVEN)

        var distancia = getDistance(dist.latitude,
            dist.longitude,
            marker.position.latitude,
            marker.position.longitude,"walking"
        )?.toFloat()?.div(1000)?.toBigDecimal()?.setScale(2, RoundingMode.HALF_EVEN)

        val view = LayoutInflater.from(context).inflate(R.layout.costum_map_window,null)

        view.findViewById<RatingBar>(R.id.ratingBar).rating= place.mediaReviews
        view.findViewById<TextView>(R.id.txt_title).text = place.nome
        view.findViewById<TextView>(R.id.txt_address).text = " Distancia: " + distancia + " Km"
        view.findViewById<TextView>(R.id.preco).text = "Preço: " + place.preco + "€"
        view.findViewById<TextView>(R.id.info).text = "(Click aqui para finalizar Marcação)"
        var walk = tempoWalking?.let { splitToComponentTimes(it.toBigDecimal()) }
        var drive = tempoCar?.let { splitToComponentTimes(it.toBigDecimal()) }
        var bike = tempoBike?.let { splitToComponentTimes(it.toBigDecimal()) }

        if(walk?.get(0) == 0)
            view.findViewById<TextView>(R.id.walk).text  = " A pé: " + walk?.get(1)+"min "+walk?.get(2)+" s"
        else
            view.findViewById<TextView>(R.id.walk).text  = " A pé: " + walk?.get(0) + "h "+walk?.get(1)+"min "+walk?.get(2)+" s"

        if(drive?.get(0) == 0)
            view.findViewById<TextView>(R.id.car).text  = " De carro: " + drive?.get(1)+"min "+drive?.get(2)+" s"
        else
            view.findViewById<TextView>(R.id.car).text  = " De carro: " + drive?.get(0) + "h "+drive?.get(1)+"min "+drive?.get(2)+" s"

        if(bike?.get(0) == 0)
            view.findViewById<TextView>(R.id.bike).text  = " De bicicleta: " + bike?.get(1)+"min "+bike?.get(2)+" s"
        else
            view.findViewById<TextView>(R.id.bike).text  = " De bicicleta: " + bike?.get(0) + "h "+bike?.get(1)+"min "+bike?.get(2)+" s"

        return view
    }

    /**
     * gets the estimated time between 2 locations
     * @param lat1 inicial point lat
     * @param lon1 inicial point long
     * @param lat2 final point lat
     * @param lon2 final point long
     * @param transporte transportation method
     * @returns estimated time
     */

    fun getTime(lat1: Double, lon1: Double, lat2: Double, lon2: Double, transporte:String): String? {
        var distance=""
        var response: String

        val thread = Thread {
            try {
                val url =
                    URL("https://api.mapbox.com/directions/v5/mapbox/$transporte/$lon1,$lat1;$lon2,$lat2?geometries=geojson&access_token=pk.eyJ1IjoibWFyb3RvMTIzNDUiLCJhIjoiY2t4bmE1dm4wMjZpNDJya2p0dWRrangwbCJ9.hPFUdTzeFL2PHhBhKjvvzg")
                //URL("https://maps.googleapis.com/maps/api/directions/json?origin=$lat1,$lon1&destination=$lat2,$lon2&sensor=false&units=metric&mode=driving&key=AIzaSyChtywxYKvHWptRLV3zjpI_yPjA-SVf4SM")
                Log.v("urldirection", url.toString())

                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"

                val inp = BufferedInputStream(conn.inputStream)
                response = org.apache.commons.io.IOUtils.toString(inp, "UTF-8")
                val jsonObject = JSONObject(response)
                //Log.e("AAA", "code: "+jsonObject)

                val array = jsonObject.getJSONArray("routes")
                val routes = array.getJSONObject(0)
                val legs = routes.getJSONArray("legs")
                //Log.e("AAA", "code: "+legs)

                val steps = legs.getJSONObject(0)
                distance = steps.getString("duration")
                Log.e("THREAD", distance)


            } catch (e: JSONException) {
                Log.e(TAG, e.toString() )
            } catch (e: IOException) {
                Log.e(TAG, e.toString() )
            }
        }
        thread.start()
        thread.join()

        return distance
    }

    /**
     * gets the estimated distance between 2 locations
     * @param lat1 inicial point lat
     * @param lon1 inicial point long
     * @param lat2 final point lat
     * @param lon2 final point long
     * @param transporte transportation method
     * @returns estimated distance
     */
    fun getDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double, transporte:String): String? {
        var distance=""
        var response: String

        val thread = Thread {
            try {
                val url =
                    URL("https://api.mapbox.com/directions/v5/mapbox/$transporte/$lon1,$lat1;$lon2,$lat2?geometries=geojson&access_token=pk.eyJ1IjoibWFyb3RvMTIzNDUiLCJhIjoiY2t4bmE1dm4wMjZpNDJya2p0dWRrangwbCJ9.hPFUdTzeFL2PHhBhKjvvzg")
                //URL("https://maps.googleapis.com/maps/api/directions/json?origin=$lat1,$lon1&destination=$lat2,$lon2&sensor=false&units=metric&mode=driving&key=AIzaSyChtywxYKvHWptRLV3zjpI_yPjA-SVf4SM")
                Log.v("urldirection", url.toString())

                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"

                val inp = BufferedInputStream(conn.inputStream)
                response = org.apache.commons.io.IOUtils.toString(inp, "UTF-8")
                val jsonObject = JSONObject(response)
                //Log.e("AAA", "code: "+jsonObject)

                val array = jsonObject.getJSONArray("routes")
                val routes = array.getJSONObject(0)
                val legs = routes.getJSONArray("legs")
                //Log.e("AAA", "code: "+legs)

                val steps = legs.getJSONObject(0)
                distance = steps.getString("distance")
                Log.e("THREAD", distance)


            } catch (e: JSONException) {
                Log.e(TAG, e.toString() )
            } catch (e: IOException) {
                Log.e(TAG, e.toString() )
            }
        }
        thread.start()
        thread.join()

        return distance
    }
}
/**
 * Transformes seconds into hh,mm,ss
 * @param seconds inicial point lat
 * @returns time
 */
fun splitToComponentTimes(seconds: BigDecimal): IntArray? {
    val day = TimeUnit.SECONDS.toDays(seconds.toLong()).toInt()
    val hours = TimeUnit.SECONDS.toHours(seconds.toLong()) - day * 24
    val minute = TimeUnit.SECONDS.toMinutes(seconds.toLong()) - TimeUnit.SECONDS.toHours(seconds.toLong()) * 60
    val second = TimeUnit.SECONDS.toSeconds(seconds.toLong()) - TimeUnit.SECONDS.toMinutes(seconds.toLong()) * 60

    return intArrayOf(hours.toInt(), minute.toInt(), second.toInt())
}

//https://api.mapbox.com/directions/v5/mapbox/cycling/-8.15335507817781,41.1838002532495;-8.151073004663317,41.184682413177825?geometries=geojson&access_token=pk.eyJ1IjoibWFyb3RvMTIzNDUiLCJhIjoiY2t4bmE1dm4wMjZpNDJya2p0dWRrangwbCJ9.hPFUdTzeFL2PHhBhKjvvzg