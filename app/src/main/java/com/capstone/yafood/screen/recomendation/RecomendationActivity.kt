package com.capstone.yafood.screen.recomendation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.capstone.yafood.R
import com.capstone.yafood.databinding.ActivityRecomendationBinding
import com.capstone.yafood.utils.RESULT_SNAP

class RecomendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecomendationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        binding.btnBack.setOnClickListener { finish() }
    }

    fun actionBack() {
        finish()
    }
}