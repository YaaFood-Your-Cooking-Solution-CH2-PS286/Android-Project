package com.capstone.yafood.screen.settings

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
    private var currentImageUri: Uri? = null
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
                viewModel.updateProfile()
            }
            it.inputName.setText(currentName)
            it.inputEmail.setText(currentEmail)
            it.btnLogout.setOnClickListener {
                showLogoutConfirmation()
            }
            it.inputName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        viewModel.setFormProfile(name = it.toString())
                    }
                }
            })
            it.inputEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        viewModel.setFormProfile(email = it.toString())
                    }
                }
            })
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

        viewModel.imageUri.observe(this) {
            if (it == null) {
                bind.btnChangePhoto.apply {
                    text = getString(R.string.edit_photo_profile)
                    setOnClickListener {
                        startGallery()
                    }
                }
                viewModel.userProfile.value?.let { user ->
                    if (user is UserState.Success) {
                        Glide.with(this).load(user.data.photoUrl).into(bind.userPhotoProfile)
                    }
                }
            } else {
                bind.userPhotoProfile.setImageURI(it)
                bind.btnChangePhoto.apply {
                    text = getString(R.string.save_update)
                    setOnClickListener {
                        viewModel.updatePhoto()
                    }
                }
            }
        }
        viewModel.loading.observe(this) {
            if (it) {
                bind.loadingContrainer.visibility = View.VISIBLE
                bind.loadingBar.visibility = View.VISIBLE
                loadingAnim()
            } else {
                bind.loadingContrainer.visibility = View.GONE
                bind.loadingBar.visibility = View.GONE
            }
        }
    }

    private fun showLogoutConfirmation() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle(getString(R.string.label_logout))
            setMessage(getString(R.string.logout_confirmation))
            setPositiveButton(getString(R.string.yes)) { dialog, id ->
                viewModel.logout()
                Runtime.getRuntime().exit(0)
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, id ->
                dialog.dismiss()
            }
        }
        val dialog = alertDialog.create()
        dialog.show()
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            viewModel.imageUri.value = uri
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun loadingAnim() {
        binding?.let {
            val postY = it.loadingBar.translationY
            ObjectAnimator.ofFloat(it.loadingBar, View.TRANSLATION_Y, postY + 1000, postY)
                .apply {
                    duration = 200
                    repeatCount = 0
                }.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}