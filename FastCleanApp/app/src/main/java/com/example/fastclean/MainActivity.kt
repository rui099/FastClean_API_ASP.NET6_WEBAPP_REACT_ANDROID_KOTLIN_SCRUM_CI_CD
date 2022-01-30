package com.example.fastclean

import android.Manifest
import android.app.Application
import android.content.*
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.fastclean.Adapters.ChatAdapter
import com.example.fastclean.Adapters.MarcacaoAdapter
import com.example.fastclean.Adapters.PedidoAdapter
import com.example.fastclean.Fragments.Chat.ChatDetalhesFragment
import com.example.fastclean.Fragments.Chat.ChatFragment
import com.example.fastclean.Fragments.Home.HomeFragment
import com.example.fastclean.Fragments.Marcacao.MarcacaoDetalhesFragment
import com.example.fastclean.Fragments.Marcacao.MarcacaoFragment
import com.example.fastclean.Fragments.Marcacao.PedidosDetalhesFragment
import com.example.fastclean.Fragments.Marcacao.PedidosFragment
import com.example.fastclean.Fragments.Marcar.EscolherTipoLimpezaFragment
import com.example.fastclean.Fragments.Perfil.ClientProfileFragment
import com.example.fastclean.Fragments.Perfil.DefinicoesFuncionarioFragment
import com.example.fastclean.Fragments.Perfil.WorkerProfileFragment
import com.example.fastclean.Models.Chat.Chat
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Utilizador.Localizacao
import com.example.fastclean.Repositories.SubRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Perfil.SubViewModel
import com.example.fastclean.ViewModels.Perfil.SubViewModelFactory
import com.example.fastclean.services.ForegroundOnlyLocationService
import com.example.fastclean.services.SharedPreferenceUtil
import com.example.fastclean.services.toText
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import java.util.ArrayList
import okhttp3.OkHttpClient
import java.lang.Exception
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

private const val TAG = "MainActivity"
private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34

class MainActivity : AppCompatActivity(),PedidoAdapter.PedidoAdapterCommunications, MarcacaoAdapter.MarcacaoAdapterCommunications,
    ChatAdapter.ChatAdapterCommunications, SharedPreferences.OnSharedPreferenceChangeListener,
    DefinicoesFuncionarioFragment.PerfilComunication, WorkerProfileFragment.atualizarLocalizacao {


    private lateinit var viewModel : SubViewModel
    private val retrofitService = RetrofitService.getInstance()
    private val firebaseInstance = FirebaseMessaging.getInstance()


    private var foregroundOnlyLocationServiceBound = false

    // Provides location updates for while-in-use feature.
    private var foregroundOnlyLocationService: ForegroundOnlyLocationService? = null

    // Listens for location broadcasts from ForegroundOnlyLocationService.
    private lateinit var foregroundOnlyBroadcastReceiver: ForegroundOnlyBroadcastReceiver

    private lateinit var sharedPreferences: SharedPreferences


    // Monitors connection to the while-in-use service.
    private val foregroundOnlyServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as ForegroundOnlyLocationService.LocalBinder
            foregroundOnlyLocationService = binder.service
            foregroundOnlyLocationServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            foregroundOnlyLocationService = null
            foregroundOnlyLocationServiceBound = false
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foregroundOnlyBroadcastReceiver = ForegroundOnlyBroadcastReceiver()

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)


        var actionBar = supportActionBar
        actionBar?.hide()
        var navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        navBar.background = null
        //navBar.menu.getItem(1).isEnabled = false

        firebaseInstance.subscribeToTopic(UserSession.getId().toString());
        UserSession.setfirebaseinstace(firebaseInstance)

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_container, HomeFragment.newInstance(),"characterList")
                .commit()
        }
        viewModel =
            ViewModelProvider(this, SubViewModelFactory(SubRepository(retrofitService))).get(
                SubViewModel::class.java
            )

        navBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.marcacoes -> {
                    replaceFragment(MarcacaoFragment.newInstance())
                    true
                }
                R.id.perfil -> {
                    if (UserSession.getRole() == "Cliente") {
                        replaceFragment(ClientProfileFragment.newInstance())
                        true
                    } else {
                        viewModel.getSub(UserSession.getId())
                        viewModel.sub.observe(this, {
                            if (it == false){
                                Toast.makeText(this, "Por favor valide a sua subscrição", Toast.LENGTH_SHORT).show()
                            }
                        })
                        replaceFragment(WorkerProfileFragment.newInstance())
                        true
                    }
                }
                R.id.home -> {
                    replaceFragment(HomeFragment.newInstance())
                    true
                }
                R.id.mensager -> {
                    replaceFragment(ChatFragment.newInstance())
                    true
                }
                else -> {
                    false
                }
            }
        }

        var coordinatorLayout = findViewById<CoordinatorLayout>(R.id.root)
        var floatingActionButton = coordinatorLayout.findViewById<FloatingActionButton>(R.id.floatingB)
        floatingActionButton.setOnClickListener{
            if (UserSession.getRole() == "Cliente")
                replaceFragment(EscolherTipoLimpezaFragment.newInstance())
            else
                replaceFragment(PedidosFragment.newInstance())
        }
    }

    fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, fragment)
            .addToBackStack(null)
            .commit()
        checkAllFalse ()
    }

    fun checkAllFalse (){
        var navBar = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        navBar.menu.get(2).setChecked(true)
    }

    override fun sendMarcacao(marcacao : Marcacao) {
        val marcacaoTemp = marcacao

        var f2 = MarcacaoDetalhesFragment()
        val bundle = Bundle()
        bundle.putSerializable("txt", marcacaoTemp)
        f2.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, f2)
            .addToBackStack(null)
            .commit()
    }

    override fun sendChat(chat: Chat) {
        val chatTemp = chat

        println(chatTemp.idFuncionario)
        println(chatTemp.idCliente)
        var f2 = ChatDetalhesFragment()
        val bundle = Bundle()
        if (UserSession.getRole() == "Cliente"){
            bundle.putString("name", chatTemp.funcionario)
            bundle.putInt("id", chatTemp.idFuncionario)
        } else {
            bundle.putString("name", chatTemp.cliente)
            bundle.putInt("id", chatTemp.idCliente)
        }
        bundle.putInt("chat", chatTemp.id)
        bundle.putParcelableArrayList("mensagens", chatTemp.mensagens as ArrayList<out Parcelable>)
        f2.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, f2)
            .addToBackStack(null)
            .commit()
    }

    override fun sendPedido(marcacao : Marcacao) {
        val marcacaoTemp = marcacao

        var f2 = PedidosDetalhesFragment()
        val bundle = Bundle()
        bundle.putSerializable("txt", marcacaoTemp)
        f2.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.root_container, f2)
            .addToBackStack(null)
            .commit()
    }

    override fun onStart() {
        super.onStart()



        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        val serviceIntent = Intent(this, ForegroundOnlyLocationService::class.java)
        bindService(serviceIntent, foregroundOnlyServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(
            foregroundOnlyBroadcastReceiver,
            IntentFilter(
                ForegroundOnlyLocationService.ACTION_FOREGROUND_ONLY_LOCATION_BROADCAST)
        )
    }

    override fun onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
            foregroundOnlyBroadcastReceiver
        )
        super.onPause()
    }

    override fun onStop() {
        if (foregroundOnlyLocationServiceBound) {
            unbindService(foregroundOnlyServiceConnection)
            foregroundOnlyLocationServiceBound = false
        }
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)

        super.onStop()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

    }

    private fun foregroundPermissionApproved(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionResult")

        when (requestCode) {
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE -> when {
                grantResults.isEmpty() ->

                    Log.d(TAG, "User interaction was cancelled.")
                grantResults[0] == PackageManager.PERMISSION_GRANTED ->

                    foregroundOnlyLocationService?.subscribeToLocationUpdates()
                else -> {


                    Snackbar.make(
                        findViewById(R.id.root),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(R.string.settings) {
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID,
                                null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                }
            }
        }
    }
    private fun requestForegroundPermissions() {
        val provideRationale = foregroundPermissionApproved()


        if (provideRationale) {
            Snackbar.make(
                findViewById(R.id.root),
                R.string.permission_rationale,
                Snackbar.LENGTH_LONG
            )
                .setAction(R.string.ok) {
                    ActivityCompat.requestPermissions(
                        this@MainActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
                    )
                }
                .show()
        } else {
            Log.d(TAG, "Request foreground only permission")
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE
            )
        }
    }


    private inner class ForegroundOnlyBroadcastReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val location = intent.getParcelableExtra<Location>(
                ForegroundOnlyLocationService.EXTRA_LOCATION
            )

            if (location != null) {
                var localizacao = Localizacao(location.latitude.toString(),location.longitude.toString())
                viewModel.updateLocalizacao(UserSession.getId(),localizacao)
            }
        }
    }

    override fun logout() {
        foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
    }

    override fun ativarLocalizacao() {

        val enabled = sharedPreferences.getBoolean(
            SharedPreferenceUtil.KEY_FOREGROUND_ENABLED, false)

        if (enabled) {
            foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
        } else {
            if (foregroundPermissionApproved()) {
                foregroundOnlyLocationService?.subscribeToLocationUpdates()
                    ?: Log.d(TAG, "Service Not Bound")
            } else {
                requestForegroundPermissions()
            }
        }
    }

    override fun desativarLocalizacao() {
        foregroundOnlyLocationService?.unsubscribeToLocationUpdates()
    }

}
