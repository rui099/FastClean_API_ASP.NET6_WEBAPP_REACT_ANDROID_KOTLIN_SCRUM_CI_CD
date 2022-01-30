package com.example.fastclean.Models.Utilizador.Cliente

import com.example.fastclean.Models.Utilizador.Morada
import com.example.fastclean.Models.Utilizador.UtilizadorUpdate
import java.io.Serializable

class ClienteUpdate(id: Int,
                    imagem: String,
                    morada: Morada,
                    contacto: Int,
                    ccFile: String,
                    cadastro: String,
                    password: String
): UtilizadorUpdate(id,
    imagem,
    morada,
    contacto,
    ccFile,
    cadastro,
    password
) , Serializable {
}