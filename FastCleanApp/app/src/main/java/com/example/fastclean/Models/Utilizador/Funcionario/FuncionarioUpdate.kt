package com.example.fastclean.Models.Utilizador.Funcionario

import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.UtilizadorUpdate
import java.io.Serializable

class FuncionarioUpdate (id: Int,
                         imagem: String,
                         morada: Morada,
                         contacto: Int,
                         ccFile: String,
                         cadastro: String,
                         password: String,
                         cartaDeConducao: String,
                         historicoMedico : String,
                         cvFile : String
): UtilizadorUpdate(id,
    imagem,
    morada,
    contacto,
    ccFile,
    cadastro,
    password
), Serializable {
}