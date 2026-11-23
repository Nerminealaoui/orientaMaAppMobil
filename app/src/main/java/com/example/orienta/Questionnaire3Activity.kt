package com.example.orienta

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class Questionnaire3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!Session.isLoggedIn(this)) {
            startActivity(
                Intent(this, LoginActivity::class.java)
                    .putExtra(Session.EXTRA_GO_QUESTIONNAIRE, true)
            )
            finish()
            return
        }

        setContentView(R.layout.activity_questionnaire)

        findViewById<TextView>(R.id.tvStepCount).text = "3 / 3"
        findViewById<ProgressBar>(R.id.progress).progress = 3
        findViewById<TextView>(R.id.tvTitle).text = "Dessinons ton parcours idéal."
        findViewById<TextView>(R.id.tvSubtitle).text =
            "Encore quelques détails pour des pistes vraiment adaptées à toi."
        findViewById<TextView>(R.id.tvHint).text =
            "Si tu n'es pas sûr, pas de souci. Tu peux modifier plus tard."
        findViewById<ImageButton>(R.id.btnBack).setOnClickListener { finish() }

        // Gonfler le contenu de l'étape 3 puis chercher les vues DANS ce contenu
        val container = findViewById<LinearLayout>(R.id.container)
        val content = LayoutInflater.from(this)
            .inflate(R.layout.content_step3, container, false)
        container.addView(content)

        val cgSubjects = content.findViewById<ChipGroup>(R.id.cgSubjects)
        val cgDuration = content.findViewById<ChipGroup>(R.id.cgDuration)
        val cgType = content.findViewById<ChipGroup>(R.id.cgType)
        val spCity = content.findViewById<Spinner>(R.id.spCity)
        val etJob = content.findViewById<EditText>(R.id.etJob)

        addChips(cgSubjects, listOf("Maths", "Physique", "SVT", "Langues"))
        addChips(cgDuration, listOf("2 ans", "3 à 5 ans", "Plus de 5 ans"))
        addChips(cgType, listOf("Public", "Privé", "Les deux"))

        val cities = listOf(
            "Casablanca", "Rabat", "Marrakech", "Fès",
            "Tanger", "Agadir", "Oujda", "Kénitra"
        )
        spCity.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, cities
        )

        val btn = findViewById<MaterialButton>(R.id.btnContinue)
        btn.isEnabled = true
        btn.text = "Voir mes résultats"

        btn.setOnClickListener {
            Session.saveSet(this, "subjects", checked(cgSubjects))
            Session.saveSet(this, "duration", checked(cgDuration))
            Session.saveSet(this, "type", checked(cgType))
            Session.saveString(this, "city", spCity.selectedItem.toString())
            Session.saveString(this, "job", etJob.text.toString().trim())

            // Prochain écran : Résultats personnalisés
            // startActivity(Intent(this, ResultsActivity::class.java))
            Toast.makeText(this, "Profil enregistré", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addChips(group: ChipGroup, labels: List<String>) {
        labels.forEach { label ->
            val chip = Chip(this).apply {
                text = label
                isCheckable = true
            }
            group.addView(chip)
        }
    }

    private fun checked(group: ChipGroup): Set<String> =
        group.checkedChipIds
            .map { id -> group.findViewById<Chip>(id).text.toString() }
            .toSet()
}