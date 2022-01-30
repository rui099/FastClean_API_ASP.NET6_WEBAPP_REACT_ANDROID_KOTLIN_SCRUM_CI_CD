package com.example.fastclean.Models.Utilizador.Funcionario

import com.example.fastclean.Models.Marcacao.Marcacao
import com.example.fastclean.Models.Review.FuncReview
import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.UtilizadorRegister
import java.io.Serializable

class FuncionarioRegister (id: Int,
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
                           listaDeChats: String,
                           cartaDeConducao: String,
                           historicoMedico : String,
                           cvFile : String,
                           subscricao : Boolean,
                           listaDeReports : String,
                           listaDeReviews : List<FuncReview>
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
) , Serializable {
}