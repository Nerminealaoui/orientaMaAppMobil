package com.example.orienta

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // À chaque lancement de l'app, on oublie la connexion précédente
        Session.setLoggedIn(this, false)

        // Commencer -> Découvrir OrientaMaroc
        findViewById<View>(R.id.btnStart).setOnClickListener {
            startActivity(Intent(this, DiscoverActivity::class.java))
        }

        // Se connecter -> Login
        findViewById<View>(R.id.btnLogin).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}