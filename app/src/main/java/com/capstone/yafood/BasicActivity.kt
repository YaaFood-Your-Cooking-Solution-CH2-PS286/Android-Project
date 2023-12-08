package com.capstone.yafood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.yafood.databinding.ActivityBasicBinding

class BasicActivity : AppCompatActivity() {
    private var binding: ActivityBasicBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.let {
            it.toHome.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            it.toLogin
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}