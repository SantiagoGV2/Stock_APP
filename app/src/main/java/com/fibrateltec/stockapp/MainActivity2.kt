package com.fibrateltec.stockapp

import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fibrateltec.stockapp.empleados.DatePickerFragment
import com.fibrateltec.stockapp.lista.Employee
import com.fibrateltec.stockapp.lista.EmployeeAdapter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity2 : AppCompatActivity() {

    private lateinit var cedula: EditText
    private lateinit var nombre: EditText
    private lateinit var estado: EditText
    private lateinit var cargo: EditText

    private lateinit var codigo: EditText
    private lateinit var descripcion: EditText
    private lateinit var estado2: EditText
    private lateinit var entrega: EditText
    private lateinit var devolucion: EditText
    private lateinit var guardar: Button
    private lateinit var actualizar: Button
    private lateinit var eliminar: Button
    private lateinit var adapter: ArrayAdapter<String> // Adaptador del Spinner
    private lateinit var spinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        cedula = findViewById(R.id.cedula) as EditText
        nombre = findViewById(R.id.nombre) as EditText
        estado = findViewById(R.id.estado) as EditText
        cargo = findViewById(R.id.cargo) as EditText

        codigo = findViewById(R.id.codigo) as EditText
        descripcion = findViewById(R.id.descripcion) as EditText
        estado2 = findViewById(R.id.estadoher) as EditText

        entrega = findViewById(R.id.entrega) as EditText
        entrega.setOnClickListener {
            mostrarDatePickerDialog(supportFragmentManager, entrega)
        }
        devolucion = findViewById(R.id.devolucion) as EditText
        devolucion.setOnClickListener {
            mostrarDatePickerDialog2(supportFragmentManager, devolucion)
        }

        guardar = findViewById(R.id.guardar) as Button
        actualizar = findViewById(R.id.actualizar) as Button
        eliminar = findViewById(R.id.eliminar) as Button

        spinner = findViewById(R.id.id_users) as Spinner
        // Configurar el adaptador del spinner
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        // Llamar a la función para obtener los ID de usuarios desde el servidor
        obtenerIdUsuarios()

        // Asignar el listener al botón guardar
        guardar.setOnClickListener {
            if (spinner.selectedItem != null) { // Verificar si hay un elemento seleccionado en el spinner
                val idUsuarioSeleccionado =
                    spinner.selectedItem.toString() // Obtener ID de usuario seleccionado en el spinner
                ejecutarServicio(
                    "http://192.168.1.38/conexion/insertar_empleados.php",
                    idUsuarioSeleccionado
                )

                ejecutarServicio2("http://192.168.1.38/conexion/insertar_datos.php")
            } else {
                Toast.makeText(applicationContext, "Spinner vacío", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun mostrarDatePickerDialog(fragmentManager: FragmentManager, entrega: EditText) {
        val datePicker = DatePickerFragment { day, month, year ->
            onDateSelected(day, month, year, entrega)
        }
        datePicker.show(fragmentManager, "datePicker")
    }

    fun mostrarDatePickerDialog2(fragmentManager: FragmentManager, devolucion: EditText) {
        val datePicker = DatePickerFragment { day, month, year ->
            onDateSelected2(day, month, year, devolucion)
        }
        datePicker.show(fragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int, entrega: EditText) {
        entrega.setText("$day/$month/$year")
    }

    private fun onDateSelected2(day: Int, month: Int, year: Int, devolucion: EditText) {
        devolucion.setText("$day/$month/$year")
    }

    private fun obtenerIdUsuarios() {
        val url =
            "http://192.168.1.38/conexion/verificar.php" // URL del archivo PHP para obtener ID de usuarios
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Procesar la respuesta del servidor y agregar los ID de usuarios al adaptador del spinner
                val idUsuarios = response.split(",")
                    .toTypedArray() // Suponiendo que la respuesta del servidor es una cadena separada por comas
                val idUsuariosLista = idUsuarios.toList()
                adapter.addAll(idUsuariosLista)
            },
            { error ->
                Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            })

        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }


    private fun ejecutarServicio(url: String, idUsuario: String) {
        val stringRequest = object : StringRequest(Method.POST, url,
            { response ->
                Toast.makeText(applicationContext, "OPERACION EXITOSA", Toast.LENGTH_SHORT).show()
            },
            { error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val parametros = HashMap<String, String>()
                parametros["emple_cedula"] = cedula.text.toString()
                parametros["emple_nombre"] = nombre.text.toString()
                parametros["emple_estado"] = estado.text.toString()
                parametros["emple_cargo"] = cargo.text.toString()
                parametros["usu_id"] = idUsuario // Pasar el ID de usuario seleccionado
                return parametros
            }
        }

        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }


    private fun ejecutarServicio2(url: String) {

        val stringRequest = object : StringRequest(Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(applicationContext, "OPERACION EXITOSA", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val parametros = HashMap<String, String>()
                parametros.put("her_cod", codigo.text.toString())
                parametros.put("her_descripcion", descripcion.text.toString())
                parametros.put("her_estado", estado2.text.toString())
                parametros.put("her_fecha_entrega", entrega.text.toString())
                parametros.put("her_fecha_devolucion", devolucion.text.toString())
                return parametros
            }

        }

        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
}
