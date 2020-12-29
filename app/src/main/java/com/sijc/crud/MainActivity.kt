package com.sijc.crud

import android.content.ContentValues
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val datoDB = admin.writableDatabase
            val registro = ContentValues()
            registro.put("codigo", txt_codigo.text.toString())
            registro.put("nombre", txt_nombre.text.toString())
            registro.put("fono", txt_fono.text.toString())
            datoDB.insert("contacto", null, registro)
            datoDB.close()
            txt_codigo.setText(" ")
            txt_nombre.setText(" ")
            txt_fono.setText(" ")
            Toast.makeText(this,"Datos registrados correctamente", Toast.LENGTH_SHORT).show()

            //mensaje en dialogos
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Datos registrados correctamente")
                // cancelable
                .setCancelable(false)
                // accion dle boton ok
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })
                // negative button text and action
               .setNegativeButton("Regresar", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            // crear el dialogo
            val alert = dialogBuilder.create()
            // titulo de la alerta
            alert.setTitle("Mensaje de confirmacion")
            //mostrar el dialogo
            alert.show()
        }

        button2.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null, 1)
            val datosDb = admin.writableDatabase
            val consulta = datosDb.rawQuery("select nombre, fono from contacto where codigo = ${txt_codigo.text.toString()}", null)
            if (consulta.moveToFirst()){
                txt_nombre.setText(consulta.getString(0))
                txt_fono.setText(consulta.getString(1))

            } else {
                Toast.makeText(this, "No se encontraron los datos", Toast.LENGTH_SHORT).show()
                datosDb.close()
            }
        }
        button3.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion", null,1)
            val datosDb = admin.writableDatabase
            val update = ContentValues()
            update.put("nombre",txt_nombre.text.toString())
            update.put("fono",txt_fono.text.toString())
            val cant = datosDb.update("contacto",update,"codigo = ${txt_codigo.text.toString()}",null)
            datosDb.close()
            if (cant == 1){
                Toast.makeText(this,"Datos Actualizados, con exito",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this, "No existe los datos", Toast.LENGTH_SHORT).show()
            }
        }

        button4.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this, "administracion", null,1)
            val datosDB = admin.writableDatabase
            val cant = datosDB.delete("contacto","codigo = ${txt_codigo.text.toString()}", null)
            datosDB.close()
            txt_codigo.text.clear()
            txt_nombre.text.clear()
            txt_fono.text.clear()
            if (cant == 1)
                Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe los datos",Toast.LENGTH_SHORT).show()
        }

    }
}