package com.example.orienta

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivToggle: ImageView
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        ivToggle = findViewById(R.id.ivTogglePassword)

        ivToggle.setOnClickListener { togglePassword() }

        findViewById<TextView>(R.id.tvForgot).setOnClickListener {
            Toast.makeText(this, "Mot de passe oublié", Toast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.tvSignUp).setOnClickListener {
            Toast.makeText(this, "Inscription", Toast.LENGTH_SHORT).show()
        }

        findViewById<View>(R.id.btnLogin).setOnClickListener { login() }
    }

    private fun togglePassword() {
        passwordVisible = !passwordVisible
        etPassword.inputType = if (passwordVisible) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        etPassword.setSelection(etPassword.text.length)
        ivToggle.alpha = if (passwordVisible) 1f else 0.5f
    }

    private fun login() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Adresse e-mail invalide"
            return
        }
        if (password.length < 6) {
            etPassword.error = "6 caractères minimum"
            return
        }

        // TODO : remplacer par un vrai appel API / Firebase Auth.
        // Pour l'instant, toute combinaison valide est acceptée.
        onLoginSuccess(email)
    }

    private fun onLoginSuccess(email: String) {
        Session.setLoggedIn(this, true)
        Session.saveString(this, "email", email)

        val goQuestionnaire = intent.getBooleanExtra(Session.EXTRA_GO_QUESTIONNAIRE, false)
        val target = if (goQuestionnaire) {
            Questionnaire1Activity::class.java
        } else {
            DiscoverActivity::class.java
        }
        startActivity(Intent(this, target))
        finish()
    }
}