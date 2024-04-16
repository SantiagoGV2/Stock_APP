package com.fibrateltec.stockapp.herramientas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.fibrateltec.stockapp.R
import com.fibrateltec.stockapp.empleados.DatePickerFragment
import org.json.JSONArray
import org.json.JSONException

class Herramientas2 : AppCompatActivity() {

    private lateinit var codigo: EditText
    private lateinit var descripcion: EditText
    private lateinit var estado2: EditText
    private lateinit var entrega: EditText
    private lateinit var devolucion: EditText
    private lateinit var observacion : EditText
    private lateinit var agregar : Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var herramientasAdapter: HerramientasAdapter
    private lateinit var herramientasList: MutableList<HerramientasAdapter.Herramienta>
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<String> // Adaptador del Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_herramientas)

        val cedulaEmpleado = intent.getStringExtra("CEDULA_EMPLEADO")


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
        observacion = findViewById(R.id.observacion) as EditText

        agregar = findViewById(R.id.agregar) as Button
        recyclerView = findViewById(R.id.listas)
        herramientasList = mutableListOf()
        herramientasAdapter = HerramientasAdapter(herramientasList)

        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = herramientasAdapter

        spinner = findViewById(R.id.cedulas) as Spinner
        // Configurar el adaptador del spinner
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        obtenerCedulas()

        // Agregar listener para el botón agregar
        agregar.setOnClickListener {
            if (spinner.selectedItem != null) { // Verificar si hay un elemento seleccionado en el spinner
                val cedulaSeleccionado =
                    spinner.selectedItem.toString() // Obtener ID de usuario seleccionado en el spinner
                ejecutarServicioInsertar(
                    "http://192.168.1.38/conexion/insertar_datos.php",
                    cedulaSeleccionado
                )
            } else {
                Toast.makeText(applicationContext, "Spinner vacío", Toast.LENGTH_SHORT).show()
            }
        }

        // Llamar método para cargar datos desde PHP
        cargarDatosDesdePHP(cedulaEmpleado)
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
    private fun obtenerCedulas() {
        val url =
            "http://192.168.1.38/conexion/verificar_cedulas.php" // URL del archivo PHP para obtener ID de usuarios
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Procesar la respuesta del servidor y agregar los ID de usuarios al adaptador del spinner
                val cedula = response.split(",")
                    .toTypedArray() // Suponiendo que la respuesta del servidor es una cadena separada por comas
                val cedulaLista = cedula.toList()
                adapter.addAll(cedulaLista)
            },
            { error ->
                Toast.makeText(applicationContext, "Error: ${error.message}", Toast.LENGTH_SHORT)
                    .show()
            })

        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
    private fun limpiarCampos() {
        codigo.text.clear()
        descripcion.text.clear()
        estado2.text.clear()
        entrega.text.clear()
        devolucion.text.clear()
        observacion.text.clear()

    }

    private fun ejecutarServicioInsertar(url: String, cedula:String) {

        val codigoText = codigo.text.toString().trim()
        val descripcionText = descripcion.text.toString().trim()
        val estadoText = estado2.text.toString().trim()
        val entregaText = entrega.text.toString().trim()
        val devolucionText = devolucion.text.toString().trim()
        val observacionText = observacion.text.toString().trim()

        // Verificar si algún campo está vacío
        if (codigoText.isEmpty() || descripcionText.isEmpty() || estadoText.isEmpty() ||
            entregaText.isEmpty() || devolucionText.isEmpty() || observacionText.isEmpty()
        ) {
            // Mostrar un mensaje de error indicando qué campo está vacío
            Toast.makeText(
                applicationContext,
                "Todos los campos son obligatorios",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener { response ->
                Toast.makeText(applicationContext, "OPERACION EXITOSA", Toast.LENGTH_SHORT).show()
                limpiarCampos()
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
                parametros.put("her_observacion", observacion.text.toString())
                parametros["emple_cedula"] = cedula // Pasar el ID de usuario seleccionado

                return parametros
            }
        }

        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }


    private fun cargarDatosDesdePHP(cedulaEmpleado: String?) {
        val url = "http://192.168.1.38/conexion/lista_her.php?cedula=$cedulaEmpleado"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val herramientasEmpleado = mutableListOf<HerramientasAdapter.Herramienta>()
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val empleadoCedula = jsonObject.getString("emple_cedula")
                        if (empleadoCedula == cedulaEmpleado) {
                            val herramienta = HerramientasAdapter.Herramienta(
                                jsonObject.getString("her_cod"),
                                jsonObject.getString("her_descripcion"),
                                jsonObject.getString("her_estado"),
                                jsonObject.getString("her_fecha_entrega"),
                                jsonObject.getString("her_fecha_devolucion"),
                                jsonObject.getString("her_observacion"),
                                empleadoCedula
                            )
                            herramientasEmpleado.add(herramienta)
                        }
                    }
                    herramientasList.clear()
                    herramientasList.addAll(herramientasEmpleado)
                    herramientasAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Toast.makeText(applicationContext, "Error al procesar los datos", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            })
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }


}