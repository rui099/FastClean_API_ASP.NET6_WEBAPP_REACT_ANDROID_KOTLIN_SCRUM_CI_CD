package com.example.fastclean.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fastclean.MainActivity
import com.example.fastclean.Models.Marcacao.Marcacao

import com.example.fastclean.R

class MarcacaoAdapter(context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var macacoes = ArrayList<Marcacao>()
    private var context = context


    /**
     * Updates the adapter list
     * @param list new adapter list.
     */
    fun addItems(list: List<Marcacao>) {
        this.macacoes.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * removes all items in the adapter list
     */
    fun clearItems() {
        macacoes.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MarcacaoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.marcacoes_rv_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MarcacaoViewHolder -> {
                holder.bind(macacoes[position])
            }
        }
        holder.itemView.setOnClickListener{
            var mainActivity = context as MainActivity
            mainActivity.sendMarcacao(macacoes[position])
        }

        //tive que passar o context para o adapter como parametro e transforma-lo em context, no fragment
        // tive que usar o on attach para ir buscar o context da main ativity


    }

    override fun getItemCount(): Int {
        return macacoes.size
    }

    class MarcacaoViewHolder constructor(
        marcacaoView : View
    ) : RecyclerView.ViewHolder(marcacaoView){

        private val cliente = marcacaoView.findViewById<TextView>(R.id.cliente)
        private val funcionario = marcacaoView.findViewById<TextView>(R.id.funcionario)
        private val data= marcacaoView.findViewById<TextView>(R.id.data)
        private val numHoras= marcacaoView.findViewById<TextView>(R.id.numHoras)



        fun bind (marcacao: Marcacao){

            cliente.text = marcacao.cliente
            funcionario.text = marcacao.funcionario
            data.text = marcacao.diaHora
            numHoras.text = marcacao.numHorasPrevistas.toString()


        }


    }

    interface MarcacaoAdapterCommunications {
        fun sendMarcacao(marcacao: Marcacao)
    }




}