package com.example.fastclean.Models.Utilizador.Cliente

import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.UtilizadorRegister
import java.io.Serializable

class ClienteRegister(id: Int,
                      nome: String,
                      password: String,
                      cargo: String,
                      email: String,
                      morada: Morada,
                      contacto: Int,
                      ccfile: String,
                      imagem: String,
                      cadastro: String,
                      listaDeMarcacoes: List<Marcacao>,
                      listaDeChats: String
): UtilizadorRegister(id,
    nome,
    password,
    cargo,
    email,
    morada,
    contacto,
    ccfile,
    imagem,
    cadastro,
    listaDeMarcacoes,
    listaDeChats
), Serializable {
}