package com.example.blog_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import java.util.*

var txtNombre: EditText? = null
var txtEmail: EditText? = null
var txtTelefono: EditText? = null
var txtPass: EditText? = null
var txtConfirmPass: EditText? = null

class Register : AppCompatActivity() {
    private var passwordShowing: Boolean = false
    private var conPasswordShowin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtNombre = findViewById(R.id.nameET)
        txtEmail = findViewById(R.id.emailET)
        txtTelefono = findViewById(R.id.telefonoET)
        txtPass = findViewById(R.id.passwordET)
        txtConfirmPass = findViewById(R.id.conPasswordET)
    }

    fun clickBtnInsertar(view: View) {
        val nombre = txtNombre?.text.toString().trim()
        val email = txtEmail?.text.toString().trim()
        val telefono = txtTelefono?.text.toString().trim()
        val password = txtPass?.text.toString().trim()
        val confirmarPassword = txtConfirmPass?.text.toString().trim()

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || password.isEmpty() || confirmarPassword.isEmpty()) {
            Snackbar.make(view, "Por favor completa todos los campos", Snackbar.LENGTH_LONG).show()
            return
        }

        if (!nombre.matches(Regex("[a-zA-Z]+"))) {

            Snackbar.make(view, "El nombre solo puede contener letras", Snackbar.LENGTH_LONG).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            Snackbar.make(view, "Correo electrónico no válido", Snackbar.LENGTH_LONG).show()
            return
        }

        if (telefono.length != 10) {

            Snackbar.make(view, "El teléfono debe tener 10 dígitos", Snackbar.LENGTH_LONG).show()
            return
        }

        if (password.length < 8 || !password.matches(Regex(".*\\d.*")) || !password.matches(Regex(".*[A-Z].*"))) {
            Snackbar.make(

                view,
                "La contraseña debe tener al menos 8 caracteres, una mayúscula y un número",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        if (password != confirmarPassword) {
            Snackbar.make(view, "Las contraseñas no coinciden", Snackbar.LENGTH_LONG).show()
            return
        }

        var url = "http://192.168.1.64/findapp/insert.php"

        val queue = Volley.newRequestQueue(this)

        var resultadoPost = object : StringRequest(Method.POST, url,
            Response.Listener { response ->
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
                Snackbar.make(view, "Felicidades usuario agregado exitosamente", Snackbar.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->  Snackbar.make(view, "Error $error", Snackbar.LENGTH_LONG).show() }) {

            override fun getParams(): MutableMap<String, String>? {
                val parametros = HashMap<String, String>()
                parametros.put("name", txtNombre?.text.toString())
                parametros.put("email", txtEmail?.text.toString())
                parametros.put("phone", txtTelefono?.text.toString())
                parametros.put("password", txtPass?.text.toString())
                return parametros
            }
        }
        queue.add(resultadoPost)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            val signUpButton: AppCompatButton = findViewById(R.id.singUpBtn)

            // Establece la posición inicial del botón fuera de la pantalla
            signUpButton.translationX = signUpButton.width.toFloat()

            // Crea y configura la animación
            signUpButton.animate()
                .translationX(0f)
                .setDuration(1000)
                .withEndAction {
                    // Realiza la acción deseada después de la animación, por ejemplo, iniciar otra actividad
                }
                .start() // Inicia la animación
        }
    }

}
