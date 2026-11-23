package com.example.orienta

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.orienta.databinding.ActivityDiscoverBinding

class DiscoverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiscoverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFeatures()

        binding.btnBack.setOnClickListener { finish() }

        binding.btnDiscoverProfile.setOnClickListener {

            findViewById<View>(R.id.btnDiscoverProfile).setOnClickListener {
                startActivity(
                    Intent(this, LoginActivity::class.java)
                        .putExtra(Session.EXTRA_GO_QUESTIONNAIRE, true)
                )
            }
        }
    }

    private fun setupFeatures() {
        binding.item1.apply {
            ivIcon.setImageResource(R.drawable.ic_person)
            tvFeatureTitle.text = "Parle-nous de toi"
            tvFeatureDesc.text = "Ton niveau, tes envies et tes préférences."
        }
        binding.item2.apply {
            ivIcon.setImageResource(R.drawable.ic_explore)
            tvFeatureTitle.text = "Découvre tes pistes"
            tvFeatureDesc.text = "Des filières adaptées, triées par compatibilité."
        }
        binding.item3.apply {
            ivIcon.setImageResource(R.drawable.ic_group)
            tvFeatureTitle.text = "Avance bien entouré"
            tvFeatureDesc.text = "Des études à explorer, des réponses à partager."
        }
    }
}