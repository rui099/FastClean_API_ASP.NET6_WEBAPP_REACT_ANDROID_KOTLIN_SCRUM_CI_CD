package com.example.fastclean.RestService

import com.example.fastclean.Models.Chat.Chat
import com.example.fastclean.Models.Chat.NewChat
import com.example.fastclean.Models.Chat.SendMensagem
import com.example.fastclean.Models.Login.Login
import com.example.fastclean.Models.Login.UserInfo
import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Marcacao.MarcacaoPost
import com.example.fastclean.Models.Reports.Report
import com.example.fastclean.Models.Review.*
import com.example.fastclean.Models.Utilizador.Cliente.ClienteDetails
import com.example.fastclean.Models.Utilizador.Cliente.ClienteRegister
import com.example.fastclean.Models.Utilizador.Cliente.ClienteUpdate
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioDetails
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioRegister
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioUpdate
import com.example.fastclean.Models.Utilizador.Localizacao
import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.PasswordDTO
import com.example.fastclean.Utils.UserSession
//import com.example.fastclean.Utils.UserSession
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.lang.Exception
import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*
import com.google.gson.GsonBuilder




interface RetrofitService  {

    //Utilizadores

    @POST("Funcionarios")
    fun postFunc(@Body func : FuncionarioRegister) : Call<ResponseBody>

    @POST("Clientes")
    fun postCliente(@Body cliente: ClienteRegister) : Call<ResponseBody>

    @PUT("Funcionarios/{id}")
    fun updateFunc(@Path("id")id : Int,@Body func: FuncionarioUpdate) : Call<ResponseBody>

    @PUT("Clientes/{id}")
    fun updateCliente(@Path("id")id : Int,@Body cliente: ClienteUpdate) : Call<ResponseBody>


    @GET("DetailsFuncionariosApp/{id}")
    suspend fun getFunc(@Path("id")id : Int) : Response<FuncionarioDetails>

    @GET("DetailsClientesApp/{id}")
    suspend fun getCliente(@Path("id")id : Int) : Response<ClienteDetails>


    @PUT("Funcionarios/Indisponivel/{id}")
    fun updateEstadoIndisponivelCliente(@Path("id")id : Int) : Call<ResponseBody>

    @PUT("Funcionarios/Disponivel/{id}")
    fun updateEstadoDisponivelCliente(@Path("id")id : Int) : Call<ResponseBody>

    @PUT("Latitude_Longitude/{id}")
    fun updatelocalizaço(@Path("id")id : Int,@Body localizacao: Localizacao) : Call<ResponseBody>







    //Subscrição
    @GET("Funcionarios/VerificaSubscricao/{id}")
    fun getSub(@Path("id")id: Int) : Call<Boolean>

    @PUT("Funcionarios/AnularSubscricao/{id}")
    fun putNullSub(@Path("id")id: Int) : Call<ResponseBody>

    @PUT("Funcionarios/Subscricao/{id}")
    fun putSub(@Path("id")id: Int) : Call<ResponseBody>
    //Editar
    @PUT("alterarMorada/{id}")
    fun putMorada(@Path("id")id : Int, @Body morada: Morada) : Call<ResponseBody>

    @PUT("alterarContacto/{id}/{numero}")
    fun putContacto(@Path("id")id : Int, @Path("numero")numero : Int) : Call<ResponseBody>

    @POST("updatePassword/{id}")
    fun putPassword(@Path("id")id : Int, @Body passwordDTO: PasswordDTO) : Call<ResponseBody>



    //Login
    @POST("Login")
    fun login(@Body login : Login) : Call<UserInfo>

    //Review de Cliente
    @GET("ReviewClientes/UserReviewsMedia/{id}")
    fun getClienteReviews(@Path("id")id : Int) : Call<ClienteListaMedia>

    @GET("ReviewClientes/media30d/{id}")
    fun getClienteReviews30(@Path("id")id : Int) : Call<ClienteListaMedia>

    @GET("ReviewClientes/media15d/{id}")
    fun getClienteReviews15(@Path("id")id : Int) : Call<ClienteListaMedia>

    @GET("ReviewClientes/mediaSemestre/{id}")
    fun getClienteReviewsSemestre(@Path("id")id : Int) : Call<ClienteListaMedia>

    @GET("ReviewClientes/mediaTrimestre/{id}")
    fun getClienteReviewsTrimestre(@Path("id")id : Int) : Call<ClienteListaMedia>

    @POST("ReviewClientes")
    fun postReviewCliente(@Body review: Review) : Call<ResponseBody>




    //Review de Funcionario
    @GET("ReviewFuncionarios/UserReviewsMedia/{id}")
    fun getFuncReviews(@Path("id")id : Int) : Call<FuncListaMedia>

    @GET("ReviewFuncionarios/media30d/{id}")
    fun getFuncReviews30(@Path("id")id : Int) : Call<FuncListaMedia>

    @GET("ReviewFuncionarios/media15d/{id}")
    fun getFuncReviews15(@Path("id")id : Int) : Call<FuncListaMedia>

    @GET("ReviewFuncionarios/mediaSemestre/{id}")
    fun getFuncReviewsSemestre(@Path("id")id : Int) : Call<FuncListaMedia>

    @GET("ReviewFuncionarios/mediaTrimestre/{id}")
    fun getFuncReviewsTrimestre(@Path("id")id : Int) : Call<FuncListaMedia>

    @POST("ReviewFuncionarios")
    fun postReviewFunc(@Body review: Review) : Call<ResponseBody>


    //Marcação
    @POST("Marcacoes/Agora")
    fun postMarcacaoAgora(@Body marcacao: MarcacaoPost) : Call<ResponseBody>

    @POST("Marcacoes/Agenda_Hora")
    fun postMarcacaoDepois(@Body marcacao: MarcacaoPost) : Call<ResponseBody>

    @PUT("Marcacoes/FuncAceite/{id}")
    fun updateFuncAceita(@Path("id")id : Int) : Call<ResponseBody>

    @PUT("Marcacoes/Terminada/{id}")
    fun updateTerminarMarcacao(@Path("id")id : Int) : Call<ResponseBody>

    @GET("Marcacoes/{id}")
    fun getDetalhes(@Path("id")id : Int) : Call<Marcacao>

    @GET("ListMarcacoesADecorrer/{id}")
    fun getMarcacoesDecorrer(@Path("id")id : Int) : Call<List<Marcacao>>

    @GET("ListMarcacoesTerminadas/{id}")
    fun getMarcacoesTerminadas(@Path("id")id : Int) : Call<List<Marcacao>>

    @PUT("Marcacoes/reviewCliente/{id}")
    fun putReviewedCliente(@Path("id")id : Int) : Call<ResponseBody>

    @PUT("Marcacoes/reviewFuncionario/{id}")
    fun putReviewedFuncionario(@Path("id")id : Int) : Call<ResponseBody>

    @GET("ListMarcacoesAindaNaoAceites/{id}")
    fun getPedidos(@Path("id")id : Int) : Call<List<Marcacao>>

    @PUT("Marcacoes/FuncAceite/{id}/{idFuncionario}")
    fun putAceitarPedido(@Path("id")id : Int,
                         @Path("idFuncionario")idFuncionario : Int
    ) : Call<ResponseBody>

    @DELETE("Marcacoes/rejectMarcacao/{id}/{idFuncionario}")
    fun RecusarPedido(@Path("id")id : Int,
                      @Path("idFuncionario")idFuncionario : Int
    ) : Call<ResponseBody>

    @PUT("Marcacoes/InicioServico/{id}")
    fun iniciarMarcacao(@Path("id")id: Int) : Call<ResponseBody>


    //Chat
    @POST("Chats")
    fun postChat(@Body chat : NewChat) : Call<ResponseBody>

//    @POST("Mensagems/{idChat}/{idSender}")
//    fun sendMessage(@Path("idChat")idChat : Int,@Path("idSender")idSender : Int,@Body mensagem: SendMensagem) : Call<SendMensagem>

    @GET("ChatList/{id}")
    fun getChat(@Path("id")id: Int) : Call<ArrayList<Chat>>

    @DELETE("Chats/removerChat/{idCliente}/{idFuncionario}")
    fun deleteChat(@Path("idCliente")idCliente: Int, @Path("idFuncionario")idFuncionario: Int) : Call<ResponseBody>

//    @GET("Chats/{id}")
//    fun getChatMessages(@Path("id")id: Int) : Call<Chat>

    //Reports
    @POST("ReportClientes/Servico")
    fun reportCliente(@Body report: Report) : Call<ResponseBody>

    @POST("ReportFuncionarios/Servico")
    fun reportFuncionario(@Body report: Report) : Call<ResponseBody>

    @POST("ReportClientes/Chat")
    fun reportClienteChat(@Body report: Report) : Call<ResponseBody>

    @POST("ReportFuncionarios/Chat")
    fun reportFuncionarioChat(@Body report: Report) : Call<ResponseBody>


    //Mapa
    @GET("Marcacoes/FuncionarioDistanciaProfessional/{latitude}/{longitude}/{range}")
    suspend fun getFuncRangeProfissional(@Path("latitude")latitude : Double,
                                         @Path("longitude")longitude : Double,
                                         @Path("range")range : Int) : Response<List<FuncionarioDetails>>

    @GET("Marcacoes/FuncionarioDistanciaNormal/{latitude}/{longitude}/{range}")
    suspend fun getFuncRangePNormal(@Path("latitude")latitude : Double,
                                    @Path("longitude")longitude : Double,
                                    @Path("range")range : Int) : Response<List<FuncionarioDetails>>




    companion object {

        private val retrofitService: RetrofitService by lazy {
            val gson  = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://10.0.2.2:7067/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(createOkHttpClient())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance(): RetrofitService {
            return retrofitService
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
}


