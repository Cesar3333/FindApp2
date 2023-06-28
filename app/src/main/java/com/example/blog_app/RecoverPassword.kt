package com.example.blog_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class RecoverPassword : AppCompatActivity() {

    private val SPLASH_DURATION = 2000L // Duración de la primera animación en milisegundos
    private val STATIC_DURATION = 1000L // Duración para que la pantalla se quede estática en milisegundos
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password2)

        val handler = Handler(Looper.getMainLooper())

    }
}