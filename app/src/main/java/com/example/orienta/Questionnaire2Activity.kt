package com.example.orienta

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class Questionnaire2Activity : AppCompatActivity() {

    private val interests = listOf(
        "Santé", "Ingénierie", "Informatique", "Économie",
        "Droit", "Sciences humaines", "Arts et design", "Éducation"
    )
    private val selected = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Session.isLoggedIn(this)) {
            startActivity(Intent(this, LoginActivity::class.java)
                .putExtra(Session.EXTRA_GO_QUESTIONNAIRE, true))
            finish()
            return
        }

        setContentView(R.layout.activity_questionnaire)

        findViewById<TextView>(R.id.tvStepCount).text = "2 / 3"
        findViewById<ProgressBar>(R.id.progress).progress = 2
        findViewById<TextView>(R.id.tvTitle).text = "Qu'est-ce qui te passionne ?"
        findViewById<TextView>(R.id.tvSubtitle).text =
            "Choisis un ou plusieurs domaines. Il n'y a pas de mauvaise réponse."
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        val hint = findViewById<TextView>(R.id.tvHint)
        val btn = findViewById<MaterialButton>(R.id.btnContinue)
        val container = findViewById<LinearLayout>(R.id.container)

        val grid = GridLayout(this).apply { columnCount = 2 }
        container.addView(grid)

        interests.forEach { name ->
            val card = LayoutInflater.from(this).inflate(R.layout.item_option, grid, false)
            card.findViewById<TextView>(R.id.tvLabel).text = name
            card.layoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, 1f)
            ).apply {
                width = 0
                height = GridLayout.LayoutParams.WRAP_CONTENT
                setMargins(6, 6, 6, 6)
            }
            card.setOnClickListener {
                if (selected.contains(name)) {
                    selected.remove(name)
                    card.setBackgroundResource(R.drawable.bg_option_normal)
                    card.findViewById<View>(R.id.ivCheck).visibility = View.INVISIBLE
                } else {
                    selected.add(name)
                    card.setBackgroundResource(R.drawable.bg_option_selected)
                    card.findViewById<View>(R.id.ivCheck).visibility = View.VISIBLE
                }
                btn.isEnabled = selected.isNotEmpty()
                hint.text = "${selected.size} domaine(s) sélectionné(s)"
            }
            grid.addView(card)
        }

        btn.setOnClickListener {
            Session.saveSet(this, "interests", selected)
            startActivity(Intent(this, Questionnaire3Activity::class.java))
        }
    }
}