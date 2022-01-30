package com.example.fastclean.Fragments.Chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fastclean.Adapters.ChatRoomAdapter
import com.example.fastclean.Fragments.Reports.ReportChatFragment
import com.example.fastclean.Models.Chat.Mensagem
import com.example.fastclean.Models.Chat.Message
import com.example.fastclean.Models.Firebase.Notification
import com.example.fastclean.Models.Firebase.PushNotification
import com.example.fastclean.R
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.RestService.RetrofitServiceFirebase
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Chat.ChatViewModel
import com.example.fastclean.ViewModels.Chat.ChatViewModelFactory
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatDetalhesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatDetalhesFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private lateinit var mensagens: ArrayList<Mensagem>
    private lateinit var nome: String
    private var chatId: Int? = null
    private lateinit var viewModel: ChatViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var mDbRef: DatabaseReference
    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String
    private lateinit var messageAdapter: ChatRoomAdapter

//    lateinit var chatDetalhesAdapter: ChatDetalhesAdapter
    lateinit var send : ImageButton
    lateinit var message: EditText
    private lateinit var chatRV: RecyclerView

    lateinit var contexto: Context
    private var idOther: Int? = null
    private lateinit var messageList: ArrayList<Message>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mensagens = it.getParcelableArrayList("mensagens")!!
            nome = it.getString("name")!!
            chatId = it.getInt("chat")
            idOther = it.getInt("id")
        }
        mDbRef = FirebaseDatabase.getInstance().getReference()
        viewModel = ViewModelProvider(this, ChatViewModelFactory(ChatRepository(retrofitService))).get(
            ChatViewModel::class.java)
        messageList = ArrayList()

        senderRoom = chatId.toString() + idOther.toString() + nome + UserSession.getId().toString() + UserSession.getName()
        receiverRoom = chatId.toString() + UserSession.getId().toString() + UserSession.getName() + idOther.toString() + nome

        mDbRef.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshot in snapshot.children) {

                        val message = postSnapshot.getValue(Message::class.java)

                        messageList.add(message!!)
                        chatRV.scrollToPosition(messageAdapter.itemCount - 1)

                    }

                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        messageAdapter = ChatRoomAdapter(this.contexto, messageList)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_chat_detalhes, container, false)

        chatRV = v.findViewById(R.id.chat_recycler_view)
        message = v.findViewById(R.id.etMessage)
        send = v.findViewById(R.id.btSend)
        val report = v.findViewById<ImageButton>(R.id.report)
        var name: TextView = v.findViewById(R.id.otherName)
        name.text = nome

//        this.chatDetalhesAdapter = ChatDetalhesAdapter(contexto)
//        chatDetalhesAdapter.setMensagens(mensagens)

        val llm = LinearLayoutManager(activity)
        llm.stackFromEnd = true

        chatRV.layoutManager = llm
        chatRV.adapter = messageAdapter
//        recyclerV.adapter = this.chatDetalhesAdapter

        send.setOnClickListener(this)
        report.setOnClickListener(this)

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatDetalhesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatDetalhesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.btSend){
            if (!message.text.isBlank()){
//                viewModel.sendMessage(chatId, UserSession.getId(), SendMensagem(message.text.toString()))
//                message.text.clear()

                val messagem = message.text.toString()
                val messageObject = Message(messagem, UserSession.getName(), UserSession.getId())

                mDbRef.child("chats").child(senderRoom).child("messages").push()
                    .setValue(messageObject).addOnSuccessListener {
                        mDbRef.child("chats").child(receiverRoom).child("messages").push()
                            .setValue(messageObject)
                    }
                message.setText("")

                var token = idOther.toString()
                val notification = Notification("Nova mensagem de: ${UserSession.getName()}",
                    "$messagem")
                val notificacao = PushNotification(
                    notification,
                    "/topics/$token"
                )
                sendNotification(notificacao)
            }

//            Handler().postDelayed({
//                update()
//            }, 500)
        }

        if (v!!.id == R.id.report) {
            val fragment = ReportChatFragment()
            val bundle = Bundle()
            bundle.putInt("reported", idOther!!)
            fragment.arguments = bundle
            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.root_container, fragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
    }

//    private fun update() {
//        viewModel.getChatMessages(chatId).observe(viewLifecycleOwner,  Observer {
//            chatDetalhesAdapter.setMensagens(it.mensagens as ArrayList<Mensagem>)
//            chatDetalhesAdapter.notifyDataSetChanged()
//        })
//    }

    /**
     * Manda uma notificacao
     * @param notification notificacao a ser mandada
     */
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val retrofit = RetrofitServiceFirebase.getInstance()
            val response = retrofit.postNotification(notification)
            if(response.isSuccessful) {
                Log.d("TAG", "Response: ${Gson().toJson(notification)}")
            } else {
                Log.e("TAG", response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e("TAG", e.toString())
        }
    }
}