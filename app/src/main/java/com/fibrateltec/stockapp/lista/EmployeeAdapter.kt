package com.fibrateltec.stockapp.lista

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fibrateltec.stockapp.R
// Define la clase Employee fuera de cualquier otra clase en un archivo separado

data class Employee(val name: String, val cedula: String)

class EmployeeAdapter(private val employees: MutableList<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    // Función para actualizar la lista de empleados en el adaptador
    fun updateEmployees(newEmployees: List<Employee>) {
        employees.clear() // Limpiar la lista actual
        employees.addAll(newEmployees) // Agregar los nuevos empleados
        notifyDataSetChanged() // Notificar al RecyclerView que los datos han cambiado
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_empleados, parent, false)
        return EmployeeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentEmployee = employees[position]
        holder.textViewName.text = currentEmployee.name
        holder.textViewCedula.text = currentEmployee.cedula
    }

    override fun getItemCount() = employees.size

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.nombre_emple)
        val textViewCedula: TextView = itemView.findViewById(R.id.cedula_emple)
    }
}
