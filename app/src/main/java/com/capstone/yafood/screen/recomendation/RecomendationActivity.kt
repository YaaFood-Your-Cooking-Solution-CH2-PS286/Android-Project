package com.capstone.yafood.screen.recomendation

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.IngredientAdapter
import com.capstone.yafood.adapter.RecipeRecomendationAdapter
import com.capstone.yafood.databinding.ActivityRecomendationBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.ListSpaceDecoration
import com.capstone.yafood.utils.RESULT_SNAP
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class RecomendationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecomendationBinding
    private val viewModel by viewModels<RecomendationViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        binding.btnBack.setOnClickListener { finish() }

        setupComponents()
        viewModelObserve()
    }

    private fun setupComponents() {
        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.rvIngredients.apply {
            this.layoutManager = layoutManager
            addItemDecoration(ListSpaceDecoration(8, 8))
        }

        binding.rvRecipeRecomendation.apply {
            this.layoutManager = LinearLayoutManager(application)
            addItemDecoration(ListSpaceDecoration(verticalSpacing = 24))
        }
    }

    private fun viewModelObserve() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                UiState.Loading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    binding.contentContainer.visibility = View.INVISIBLE
                }

                is UiState.Error -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.contentContainer.visibility = View.INVISIBLE

                    binding.errorMessage.text = state.errorMessage
                }

                is UiState.Success -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.errorMessage.visibility = View.GONE
                    binding.contentContainer.visibility = View.VISIBLE

                    binding.rvIngredients.adapter = IngredientAdapter(state.data.ingredientDetected)
                    binding.rvRecipeRecomendation.adapter =
                        RecipeRecomendationAdapter(state.data.recipe)
                }
            }
        }
    }

    fun actionBack() {
        finish()
    }
}