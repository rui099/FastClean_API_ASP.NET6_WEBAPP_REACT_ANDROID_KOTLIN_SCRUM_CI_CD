package com.example.fastclean.Models.Firebase

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PushNotification(
    @SerializedName("notification")
    val data: Notification,
    val to: String
):Serializable