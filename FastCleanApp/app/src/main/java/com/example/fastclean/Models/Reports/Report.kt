package com.example.fastclean.Models.Reports

import java.io.Serializable

data class Report(
    var titulo : String,
    var descricao : String,
    var reported : Int,
    var reporter : Int
) : Serializable
