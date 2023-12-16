package com.capstone.yafood.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.yafood.screen.auth.LoginFragment
import com.capstone.yafood.screen.auth.RegisterFragment

class AuthPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        if (position == 0) LoginFragment() else RegisterFragment()

}