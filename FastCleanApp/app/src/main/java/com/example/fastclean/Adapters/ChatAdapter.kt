package com.example.fastclean.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Chat.Chat
import com.example.fastclean.R
import com.example.fastclean.Utils.UserSession

class ChatAdapter (context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var chat = ArrayList<Chat>()
    private var context = context

    /**
     * Updates the adapter list
     * @param chatList new adapter list.
     */
    fun setChatList(chatList: ArrayList<Chat>){
        this.chat = chatList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_rv_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatViewHolder -> {
                holder.bind(this.chat[position])
            }
        }
        holder.itemView.setOnClickListener{
            var mainActivity = context as MainActivity
            mainActivity.sendChat(this.chat[position])
        }

        //tive que passar o context para o adapter como parametro e transforma-lo em context, no fragment
        // tive que usar o on attach para ir buscar o context da main ativity


    }

    override fun getItemCount(): Int {
        return chat.size
    }

    class ChatViewHolder constructor(
        chatView : View
    ) : RecyclerView.ViewHolder(chatView){

        private val target = chatView.findViewById<TextView>(R.id.target)
        private val profilePic = chatView.findViewById<ImageView>(R.id.profilePic)

        val v = chatView

        fun bind (chat: Chat){

            if(UserSession.getRole() == "Cliente"){
                target.text = chat.funcionario

                val id = chat.idFuncionario

                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_perfil)
                    .error(R.drawable.ic_perfil)
                    .override(200,200)
                Glide.with(v.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load("https://10.0.2.2:7067/api/GetImage/$id")
                    .circleCrop()
                    .into(profilePic)
            } else {
                target.text = chat.cliente

                val id = chat.idCliente

                val requestOptions = RequestOptions()
                    .placeholder(R.drawable.ic_perfil)
                    .error(R.drawable.ic_perfil)
                    .override(200,200)
                Glide.with(v.context)
                    .applyDefaultRequestOptions(requestOptions)
                    .load("https://10.0.2.2:7067/api/GetImage/$id")
                    .circleCrop()
                    .into(profilePic)
            }
//            if(chat.mensagens.isNotEmpty()) {
//                lastMessage.text = chat.mensagens.last().mensagem
//            } else {
//                lastMessage.text = "Inicio de chat"
//            }
        }


    }

    interface ChatAdapterCommunications {
        fun sendChat(chat: Chat)
    }
    /**
     * Updates the adapter list
     * @param nChat new adapter list.
     */
    fun update(nChat : ArrayList<Chat>) {
        chat = nChat
        this.notifyDataSetChanged()
    }
}