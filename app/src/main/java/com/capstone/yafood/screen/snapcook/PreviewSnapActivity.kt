package com.capstone.yafood.screen.snapcook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.capstone.yafood.R
import com.capstone.yafood.databinding.ActivityPreviewSnapBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.screen.recomendation.RecomendationActivity
import com.capstone.yafood.utils.RESULT_SNAP

class PreviewSnapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewSnapBinding
    private val snapViewModel by viewModels<SnapViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewSnapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(RESULT_SNAP)
        imageUri?.let {
            snapViewModel.setImageUri(imageUri)
            binding.imageView.setImageURI(it.toUri())
        }

        setActions()
    }

    private fun setActions() {
        binding.apply {
            btnBack.setOnClickListener { finish() }
            btnSubmit.setOnClickListener {
//                snapViewModel.submitImage()
                startToRecomendation()
            }
        }
    }

    private fun startToRecomendation() {
        val toRecomendation = Intent(this, RecomendationActivity::class.java)
        toRecomendation.putExtra(RESULT_SNAP, snapViewModel.imageUri.value)
        startActivity(toRecomendation)
        finish()
    }
}