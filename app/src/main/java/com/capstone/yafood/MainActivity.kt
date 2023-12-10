package com.capstone.yafood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.yafood.databinding.ActivityMainBinding
import com.capstone.yafood.screen.snapcook.SnapCookActivity

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.let {
            it.bottomNavigationView.setupWithNavController(findNavController(R.id.fragment_container))
            it.btnOpenCamera.setOnClickListener {
                startActivity(Intent(this, SnapCookActivity::class.java))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}