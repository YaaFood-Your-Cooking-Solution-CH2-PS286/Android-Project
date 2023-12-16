package com.capstone.yafood.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.capstone.yafood.MainActivity
import com.capstone.yafood.R
import com.capstone.yafood.screen.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private val viewModel by viewModels<SplashViewModel> { ViewModelFactory.getInstance(application) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val toHome = Intent(this, MainActivity::class.java)
        viewModel
        activityScope.launch {
            delay(DURATION)
            runOnUiThread {
                startActivity(toHome)
                finish()
            }
        }
    }

    companion object {
        const val DURATION = 2000L
    }
}