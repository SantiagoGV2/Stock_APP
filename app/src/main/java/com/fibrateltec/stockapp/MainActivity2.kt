package com.fibrateltec.stockapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fibrateltec.stockapp.empleados.FragmentLista

class MainActivity2 : AppCompatActivity(), FragmentLista.SelectorElementos  {

    val descripcion = arrayOf("Descripcion1", "Descripcion2", "Descripcion3","Descripcion4","Descripcion5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
    }

    override fun EscogerElemento(posicion: Int) {
        val tv : TextView = findViewById(R.id.title2)
        tv.text = descripcion[posicion]
        //Si la orientacion es vertical has esto
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            val manager = supportFragmentManager
            manager.beginTransaction()
                .show(manager.findFragmentById(R.id.fragmentContainerView4)!!) //muestra el frgamento 2
                .hide(manager.findFragmentById(R.id.fragmentContainerView3)!!)//oculta el 1
                .addToBackStack(null)//vulvo con la flecha atras
                .commit()//publico los cambios
        }else{
            val manager = supportFragmentManager
            manager.beginTransaction()
                .show(manager.findFragmentById(R.id.fragmentContainerView4)!!)
                .show(manager.findFragmentById(R.id.fragmentContainerView3)!!)
                .commit()
        }
    }

}