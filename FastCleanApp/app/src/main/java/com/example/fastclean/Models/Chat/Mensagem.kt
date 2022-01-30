package com.example.fastclean.Models.Chat

import android.os.Parcel
import android.os.Parcelable

data class Mensagem(
    val nomeSender: String?,
    val mensagem: String?,
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nomeSender)
        parcel.writeString(mensagem)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mensagem> {
        override fun createFromParcel(parcel: Parcel): Mensagem {
            return Mensagem(parcel)
        }

        override fun newArray(size: Int): Array<Mensagem?> {
            return arrayOfNulls(size)
        }
    }
}
