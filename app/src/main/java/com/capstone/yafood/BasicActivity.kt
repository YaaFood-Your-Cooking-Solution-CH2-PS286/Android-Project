package com.capstone.yafood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.yafood.databinding.ActivityBasicBinding
import com.capstone.yafood.screen.auth.AuthActivity
import com.capstone.yafood.screen.createarticle.CreateArticleActivity

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
            it.toLogin.setOnClickListener {
                startActivity(Intent(this, AuthActivity::class.java))
            }
            it.toCreateArticle.setOnClickListener {
                startActivity(Intent(this, CreateArticleActivity::class.java))
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}