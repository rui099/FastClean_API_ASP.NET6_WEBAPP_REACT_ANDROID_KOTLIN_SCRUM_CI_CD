package com.example.fastclean.Models.Chat

class Message {
    var message: String? = null
    var senderName: String? = null
    var senderId: Int? = null

    constructor()

    constructor(message: String?, senderName: String?, senderId: Int?){
        this.message = message
        this.senderName = senderName
        this.senderId = senderId
    }
}