package com.example.orienta

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileIntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_intro)

        findViewById<View>(R.id.ivBack).setOnClickListener { finish() }

        findViewById<View>(R.id.btnDiscover).setOnClickListener {
            // TODO : ouvrir la première question du questionnaire
            // startActivity(Intent(this, QuestionnaireActivity::class.java))
            Toast.makeText(this, "Début du questionnaire", Toast.LENGTH_SHORT).show()
        }
    }
}