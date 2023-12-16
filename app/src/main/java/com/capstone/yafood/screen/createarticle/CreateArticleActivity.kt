package com.capstone.yafood.screen.createarticle

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.yafood.MainActivity
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.IngredientInputAdapter
import com.capstone.yafood.adapter.StepInputAdapter
import com.capstone.yafood.databinding.ActivityCreateArticleBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.ListSpaceDecoration
import com.capstone.yafood.utils.dpValue

class CreateArticleActivity : AppCompatActivity() {

    private var binding: ActivityCreateArticleBinding? = null
    private val viewModel by viewModels<CreateArticleViewModel> {
        ViewModelFactory.getInstance(application)
    }
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateArticleBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupComponents()
    }

    private fun setupComponents() {
        binding?.let {
            viewModelObserver(it)
            it.rvIngredientsInput.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(ListSpaceDecoration(verticalSpacing = 16))
            }
            it.rvStepsInput.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                addItemDecoration(ListSpaceDecoration(verticalSpacing = 16))
            }
            it.btnAddIngredientInput.setOnClickListener { _ ->
                viewModel.addIngredientInput()
            }
            it.btnAddStepInput.setOnClickListener {
                viewModel.addStepInput()
            }
            it.btnPost.setOnClickListener { _ ->
                viewModel.postArticle(
                    it.titleInput.text.toString(),
                    it.descriptionInput.text.toString()
                )
            }

            it.imagePlaceholder.setOnClickListener {
                startGallery()
            }
            it.btnEditImage.setOnClickListener {
                startGallery()
            }
            it.btnDeleteImage.setOnClickListener {
                currentImageUri = null
                viewModel.setImageUri(null)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModelObserver(bind: ActivityCreateArticleBinding) {
        //set RV Ingredients Input
        viewModel.listIngredientInput.observe(this) {
            bind.rvIngredientsInput.adapter = IngredientInputAdapter(
                it,
                viewModel::deleteIngredientInput,
                viewModel::updateIngredientValue
            )
        }

        //set RV Steps Input
        viewModel.listStepInput.observe(this) {
            bind.rvStepsInput.adapter = StepInputAdapter(
                it,
                viewModel::deleteStepInput,
                viewModel::updateStepValue
            )
        }

        //set Image Article
        viewModel.imageUri.observe(this) {
            if (it !== null) {
                bind.imageArticleContainer.layoutParams.height = dpValue(500f, bind.root)
                bind.imagePlaceholder.visibility = View.GONE
                bind.previewImage.apply {
                    visibility = View.VISIBLE
                    setImageURI(it.toUri())
                }
                bind.editableImageContainer.visibility = View.VISIBLE
            } else {
                bind.imageArticleContainer.layoutParams.height = dpValue(240f, bind.root)
                bind.imagePlaceholder.visibility = View.VISIBLE
                bind.editableImageContainer.visibility = View.GONE
                bind.previewImage.visibility = View.GONE
            }
        }

        //uiState
        viewModel.uiState.observe(this) {
            when (it) {
                UiState.Loading -> {
                    bind.btnPost.visibility = View.GONE
                    bind.loadingBar.visibility = View.VISIBLE
                }

                is UiState.Error -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    bind.btnPost.visibility = View.VISIBLE
                    bind.loadingBar.visibility = View.GONE

                }

                is UiState.Success -> {
                    bind.btnPost.visibility = View.VISIBLE
                    bind.loadingBar.visibility = View.GONE

                    Toast.makeText(this, it.data, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            viewModel.setImageUri(currentImageUri.toString())
        } else {
            Log.d("Image Picker", "No media selected")
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}