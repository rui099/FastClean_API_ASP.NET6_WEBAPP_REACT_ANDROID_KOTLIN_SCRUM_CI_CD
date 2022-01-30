package com.example.fastclean.Repositories

import com.example.fastclean.Models.Utilizador.Cliente.ClienteDetails
import com.example.fastclean.Models.Utilizador.Cliente.ClienteRegister
import com.example.fastclean.Models.Utilizador.Cliente.ClienteUpdate
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioDetails
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioRegister
import com.example.fastclean.Models.Utilizador.Funcionario.FuncionarioUpdate
import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.PasswordDTO
import com.example.fastclean.RestService.RetrofitService
import retrofit2.Response

class UtilizadoresRepository constructor(private val retrofitService : RetrofitService) {

    fun saveFuncionario(funcionario : FuncionarioRegister) = retrofitService.postFunc(funcionario)

    fun saveCliente(cliente : ClienteRegister) = retrofitService.postCliente(cliente)

    fun editFuncionario(id : Int,funcionario: FuncionarioUpdate) = retrofitService.updateFunc(id,funcionario)

    fun editCliente(id : Int,cliente: ClienteUpdate) = retrofitService.updateCliente(id,cliente)


    fun alterarMorada(id : Int,morada: Morada) = retrofitService.putMorada(id, morada)

    fun alterarContacto(id : Int,contacto: Int) = retrofitService.putContacto(id, contacto)

    fun alterarPassword(id : Int, passwordDTO: PasswordDTO) = retrofitService.putPassword(id, passwordDTO)


    suspend fun getFuncionario(id : Int) :Response<FuncionarioDetails>{ return retrofitService.getFunc(id)}

    suspend fun getCliente(id : Int) : Response<ClienteDetails> { return retrofitService.getCliente(id)}


    fun updateEstadoIndisponivelCliente(id : Int) = retrofitService.updateEstadoIndisponivelCliente(id)

    fun updateEstadoDisponivelCliente(id : Int) = retrofitService.updateEstadoDisponivelCliente(id)






//    fun getImage(id: Int) = retrofitService.getImage(id)

}