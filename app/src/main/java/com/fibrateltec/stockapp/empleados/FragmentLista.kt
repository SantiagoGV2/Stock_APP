package com.fibrateltec.stockapp.empleados

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment
import com.fibrateltec.stockapp.R
import java.lang.RuntimeException


class FragmentLista : ListFragment() {

    interface SelectorElementos {
        fun EscogerElemento(posicion: Int){

        }
    }
    var activityContenedora : SelectorElementos? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is SelectorElementos){
            activityContenedora = context
        }else{
            throw RuntimeException(
                context.toString() + "Debe implemetar Selector Elementos"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val nombres = arrayOf("Santiago Garcia Velandia"+"\n1011084605", "Dato2", "Dato3","Dato4","Dato5")
        listAdapter = ArrayAdapter<String>(requireActivity(),
            android.R.layout.simple_list_item_1,nombres)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        super.onListItemClick(l, v, position, id)
        activityContenedora?.EscogerElemento(position)
    }

}