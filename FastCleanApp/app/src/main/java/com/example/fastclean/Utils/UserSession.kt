package com.example.fastclean.Utils

import android.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.fastclean.LoginActivity
import com.example.fastclean.MainActivity
import com.google.firebase.messaging.FirebaseMessaging

object  UserSession {

    private var token: String? = null
    private var id : Int? = null
    private var role : String? = null
    private var name: String? = null
    private var firebaseinstace: FirebaseMessaging? = null

    fun isUserLogged(): Boolean {
        return token != null
    }

    fun setId(id:Int){
        this.id = id
    }

    fun setfirebaseinstace(firebaseinstace: FirebaseMessaging){
        this.firebaseinstace = firebaseinstace
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun setRole(role: String) {
        this.role = role
    }

    fun setName(name: String) {
        this.name = name
    }

    fun cleanSession(context : Context) {
        val sharedPref =  context.getSharedPreferences("Login", AppCompatActivity.MODE_PRIVATE)
        val Ed = sharedPref?.edit()
        Ed?.putString("token", null)
        Ed?.putString("id", null)
        Ed?.putString("cargo",null)
        Ed?.putString("nome", null)
        Ed?.commit()

        firebaseinstace?.unsubscribeFromTopic(getId().toString())

        token = null
        id = null
        role = null



        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)



    }

    fun getName(): String = name!!

    fun getId():Int = id!!

    fun getRole():String = role!!

    fun getToken(): String = if (token != null)
        token!!
    else
        ""

}