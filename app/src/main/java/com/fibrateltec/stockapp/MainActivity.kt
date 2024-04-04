package com.fibrateltec.stockapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    private lateinit var btnusuario: EditText
    private lateinit var btncontraseñas: EditText
    private lateinit var iniciar: Button
    private var usuario = ""
    private var contraseña = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnusuario = findViewById(R.id.btnusuario)
        btncontraseñas = findViewById(R.id.btncontraseña) // Cambiado de Contraseña a contraseña según el id de tu layout
        iniciar = findViewById(R.id.iniciar)

        iniciar.setOnClickListener {
            usuario = btnusuario.text.toString()
            contraseña = btncontraseñas.text.toString()
            if(!usuario.isEmpty() && !contraseña.isEmpty()) {
                validarUsuario("http://localhost/conexion/validar_usuario.php")
            }else{
                Toast.makeText(this@MainActivity, "No se permiten campos vacios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarUsuario(URL: String) {
        val stringRequest = object : StringRequest(
            Method.POST, URL,
            Response.Listener { response ->
                if (!response.isEmpty()) {
                    val intent = Intent(applicationContext, MainActivity2::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Usuario o Contraseña incorrecto", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@MainActivity, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val parametros: MutableMap<String, String> = HashMap()
                parametros["usuario"] = usuario
                parametros["contraseña"] = contraseña
                return parametros
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}

