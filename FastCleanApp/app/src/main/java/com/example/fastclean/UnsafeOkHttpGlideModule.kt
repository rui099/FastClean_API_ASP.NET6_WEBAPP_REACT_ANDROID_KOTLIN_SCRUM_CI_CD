package com.example.fastclean

import android.content.Context
import android.text.Editable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.module.LibraryGlideModule
import com.example.fastclean.Utils.UserSession
import okhttp3.OkHttpClient
import okhttp3.WebSocket
import java.io.InputStream
import java.lang.Exception
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@GlideModule
class UnsafeOkHttpGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = createOkHttpClient()
        registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(client!!)
        )
    }
    private fun createOkHttpClient(): OkHttpClient? {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory)
                .hostnameVerifier { hostname: String?, session: SSLSession? -> true }
                .addInterceptor{ chain ->
                    chain.proceed(chain.request().newBuilder().also{
                        it.addHeader("Authorization","Bearer ${UserSession.getToken()}")
                    }.build())

                }
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
