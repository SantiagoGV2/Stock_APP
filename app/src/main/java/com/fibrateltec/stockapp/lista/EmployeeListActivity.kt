package com.fibrateltec.stockapp.lista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fibrateltec.stockapp.R

class EmployeeListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var employeeAdapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vista_lista)

        val employees = mutableListOf<Employee>() // Aquí obtienes tus empleados desde tu fuente de datos

        // Agrega algunos empleados de ejemplo (reemplaza esto con tus datos reales)
        employees.add(Employee("Juan", "123456789"))
        employees.add(Employee("Maria", "987654321"))

        recyclerView = findViewById(R.id.lista_elementos)
        employeeAdapter = EmployeeAdapter(employees)
        recyclerView.adapter = employeeAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
