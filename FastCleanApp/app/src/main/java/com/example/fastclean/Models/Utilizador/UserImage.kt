package com.example.fastclean.Models.Utilizador

import android.graphics.Bitmap
import android.media.Image
import java.io.File
import java.io.Serializable

data class UserImage(
    val imagem: File
) : Serializable
