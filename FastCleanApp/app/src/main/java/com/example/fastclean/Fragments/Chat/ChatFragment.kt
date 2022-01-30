package com.example.fastclean.Fragments.Chat

import android.app.job.JobScheduler
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fastclean.Adapters.ChatAdapter
import com.example.fastclean.Models.Chat.Chat
import com.example.fastclean.R
import com.example.fastclean.Repositories.ChatRepository
import com.example.fastclean.RestService.RetrofitService
import com.example.fastclean.Utils.UserSession
import com.example.fastclean.ViewModels.Chat.ChatViewModel
import com.example.fastclean.ViewModels.Chat.ChatViewModelFactory
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var chatAdapter: ChatAdapter

    lateinit var contexto: Context
    private lateinit var viewModel: ChatViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.contexto = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        this.chatAdapter = ChatAdapter(contexto)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        this.chatAdapter = ChatAdapter(contexto)

        viewModel = ViewModelProvider(this, ChatViewModelFactory(ChatRepository(retrofitService))).get(
            ChatViewModel::class.java)



        viewModel.getChat(UserSession.getId()).observe(viewLifecycleOwner,  Observer {
            chatAdapter.setChatList(it)
            chatAdapter.notifyDataSetChanged()

        })




        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_chat, container, false)



        var recyclerV = v.findViewById(R.id.recycler_view) as RecyclerView

        val llm = LinearLayoutManager(activity)
        llm.reverseLayout = true
        llm.stackFromEnd = true
        recyclerV.layoutManager = llm

        recyclerV.adapter = this.chatAdapter

//        val scheduler = Executors.newSingleThreadScheduledExecutor()
//
//        scheduler.scheduleAtFixedRate({
//            update()
//        }, 0, 30, TimeUnit.SECONDS)

        return v
    }

    private fun update(){
        viewModel.getChat(UserSession.getId()).observe(viewLifecycleOwner, Observer {
            chatAdapter.update(it)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ChatFragment()
    }
}