package com.capstone.yafood.screen.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.capstone.yafood.R
import com.capstone.yafood.adapter.AuthPagerAdapter
import com.capstone.yafood.databinding.ActivityAuthBinding
import com.capstone.yafood.databinding.ItemStepInputBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupComponents()
    }

    private fun setupComponents() {
        binding.viewPager.adapter = AuthPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val tabTitle = resources.getString(TAB_TITLES[position])
            tab.text = tabTitle
        }.attach()
    }

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_login,
            R.string.tab_register,
        )
    }
}