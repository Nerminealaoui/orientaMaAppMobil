package com.example.orienta

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class Questionnaire1Activity : AppCompatActivity() {

    private val levels = listOf(
        "Collège", "Lycée", "Bac", "Bac+1", "Bac+2", "Bac+3 et plus", "Reconversion"
    )
    private var selected: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Session.isLoggedIn(this)) {
            startActivity(Intent(this, LoginActivity::class.java)
                .putExtra(Session.EXTRA_GO_QUESTIONNAIRE, true))
            finish()
            return
        }

        setContentView(R.layout.activity_questionnaire)

        findViewById<TextView>(R.id.tvStepCount).text = "1 / 3"
        findViewById<ProgressBar>(R.id.progress).progress = 1
        findViewById<TextView>(R.id.tvTitle).text = "Où en es-tu dans tes études ?"
        findViewById<TextView>(R.id.tvSubtitle).text =
            "Choisis ton niveau actuel pour découvrir les parcours qui te sont accessibles."
        findViewById<TextView>(R.id.tvHint).text = "Une réponse, un premier pas vers ton projet."
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val container = findViewById<LinearLayout>(R.id.container)
        val btn = findViewById<MaterialButton>(R.id.btnContinue)
        val rows = mutableListOf<View>()

        levels.forEach { level ->
            val row = LayoutInflater.from(this).inflate(R.layout.item_option, container, false)
            row.findViewById<TextView>(R.id.tvLabel).text = level
            (row.layoutParams as LinearLayout.LayoutParams).bottomMargin = 12
            row.setOnClickListener {
                selected = level
                rows.forEach {
                    it.setBackgroundResource(R.drawable.bg_option_normal)
                    it.findViewById<View>(R.id.ivCheck).visibility = View.INVISIBLE
                }
                row.setBackgroundResource(R.drawable.bg_option_selected)
                row.findViewById<View>(R.id.ivCheck).visibility = View.VISIBLE
                btn.isEnabled = true
            }
            rows.add(row)
            container.addView(row)
        }

        btn.setOnClickListener {
            selected?.let { Session.saveString(this, "level", it) }
            startActivity(Intent(this, Questionnaire2Activity::class.java))
        }
    }
}