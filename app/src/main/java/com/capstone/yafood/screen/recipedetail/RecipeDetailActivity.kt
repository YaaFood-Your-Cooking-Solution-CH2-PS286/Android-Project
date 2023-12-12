package com.capstone.yafood.screen.recipedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.adapter.OrderList
import com.capstone.yafood.adapter.OrderListAdapter
import com.capstone.yafood.adapter.UnorderList
import com.capstone.yafood.adapter.UnorderListAdapter
import com.capstone.yafood.databinding.ActivityRecipeDetailBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.RECIPE_ID

class RecipeDetailActivity : AppCompatActivity() {

    private var binding: ActivityRecipeDetailBinding? = null
    private val viewModel by viewModels<RecipeDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val recipeId = intent.getIntExtra(RECIPE_ID, -1)
        viewModel.setRecipeId(recipeId)
        setupComponents()
    }

    private fun setupComponents() {
        binding?.let {
            viewModelObserver(it)
        }
    }

    private fun viewModelObserver(bind: ActivityRecipeDetailBinding) {
        viewModel.user.observe(this) {
            Glide.with(this)
                .load(it.photoUrl)
                .placeholder(R.drawable.ic_person_circle)
                .into(bind.userPhotoProfile)
        }
        viewModel.getDetailRecipe().observe(this) {
            Glide.with(this).load(it.imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .into(bind.articleImage)
            bind.articleTitle.text = it.name
            bind.rvIngredients.adapter =
                UnorderListAdapter(it.ingredients.map { ingredient -> UnorderList(ingredient) })
            bind.rvSteps.adapter =
                OrderListAdapter(it.procedure.mapIndexed { index, step -> OrderList(index, step) })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}