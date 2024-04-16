package com.fibrateltec.stockapp.herramientas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fibrateltec.stockapp.R

class HerramientasAdapter(private val herramientas: List<Herramienta>) :
    RecyclerView.Adapter<HerramientasAdapter.HerramientaViewHolder>() {
    class Herramienta(
        val codigo: String,
        val descripcion: String,
        val estado: String,
        val entrega: String,
        val devolucion: String,
        val observacion: String,
        val cedula : String
    )

    inner class HerramientaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Aquí puedes encontrar y asignar tus vistas de item_herramienta.xml
        val codigoTextView: TextView = itemView.findViewById(R.id.codigo)
        val descripcionTextView: TextView = itemView.findViewById(R.id.descripcion)
        val estadoTextView: TextView = itemView.findViewById(R.id.estado)
        val entregaTextView: TextView = itemView.findViewById(R.id.entrega)
        val devolucionTextView: TextView = itemView.findViewById(R.id.devolucion)
        val observacionTextView: TextView = itemView.findViewById(R.id.observacion)
        val cedulaTextView: TextView = itemView.findViewById(R.id.cedula)
        // Continúa con las demás vistas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HerramientaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_herramientas, parent, false)


        return HerramientaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HerramientaViewHolder, position: Int) {
        val currentItem = herramientas[position]
        holder.codigoTextView.text = currentItem.codigo
        holder.descripcionTextView.text = currentItem.descripcion
        holder.estadoTextView.text = currentItem.estado
        holder.entregaTextView.text = currentItem.entrega
        holder.devolucionTextView.text = currentItem.devolucion
        holder.observacionTextView.text = currentItem.observacion
        holder.cedulaTextView.text = currentItem.cedula
        // Asigna los demás campos a las vistas correspondientes
    }

    override fun getItemCount() = herramientas.size
}