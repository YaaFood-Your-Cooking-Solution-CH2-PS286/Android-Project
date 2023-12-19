package com.capstone.yafood.screen.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.capstone.yafood.MainActivity
import com.capstone.yafood.R
import com.capstone.yafood.databinding.ActivitySettingsBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.USER_EMAIL
import com.capstone.yafood.utils.USER_NAME
import com.capstone.yafood.utils.UserState

class SettingsActivity : AppCompatActivity() {
    private var binding: ActivitySettingsBinding? = null
    private val viewModel by viewModels<SettingsViewModel> {
        ViewModelFactory.getInstance(
            application
        )
    }

    private var currentName: String = ""
    private var currentEmail: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        currentName = intent.getStringExtra(USER_NAME) ?: ""
        currentEmail = intent.getStringExtra(USER_EMAIL) ?: ""
        setComponents()
    }

    private fun setComponents() {
        binding?.let {
            viewModelObserver(it)
            it.btnLogout.setOnClickListener {
                viewModel.logout()
            }
            it.btnSaveEdit.setOnClickListener { _ ->
            }
            it.inputName.setText(currentName)
            it.inputEmail.setText(currentEmail)
            it.btnLogout.setOnClickListener {
                showLogoutConfirmation()
            }
        }
    }

    private fun viewModelObserver(bind: ActivitySettingsBinding) {
        viewModel.userProfile.observe(this) { state ->
            when (state) {
                UserState.Loading -> {

                }

                is UserState.Success -> {
                    state.data.let {
                        bind.inputName.setText(it.name)
                        bind.inputEmail.setText(it.email)
                        Glide.with(this).load(it.photoUrl).placeholder(R.drawable.ic_chef)
                            .error(R.drawable.ic_chef)
                            .into(bind.userPhotoProfile)
                    }
                }

                UserState.Unauthorized -> {

                }
            }
        }
    }

    private fun showLogoutConfirmation() {
        val restart = Intent(this, MainActivity::class.java)
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle(getString(R.string.label_logout))
            setMessage(getString(R.string.logout_confirmation))
            setPositiveButton(getString(R.string.yes)) { dialog, id ->
                viewModel.logout()
                Runtime.getRuntime().exit(0);
//                startActivity(restart)
//                finishAffinity()
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, id ->
                dialog.dismiss()
            }
        }
        val dialog = alertDialog.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}