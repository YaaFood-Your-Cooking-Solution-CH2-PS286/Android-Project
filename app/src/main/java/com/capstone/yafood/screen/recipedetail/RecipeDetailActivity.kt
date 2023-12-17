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
import com.capstone.yafood.utils.RECIPE_IMAGE
import com.capstone.yafood.utils.RECIPE_INGREDIENT
import com.capstone.yafood.utils.RECIPE_STEP
import com.capstone.yafood.utils.RECIPE_TITLE
import com.capstone.yafood.utils.UserState

class RecipeDetailActivity : AppCompatActivity() {

    private var binding: ActivityRecipeDetailBinding? = null
    private val viewModel by viewModels<RecipeDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//        val recipeId = intent.getIntExtra(RECIPE_ID, -1)
        val title = intent.getStringExtra(RECIPE_TITLE) ?: ""
        val imageUrl = intent.getStringExtra(RECIPE_IMAGE) ?: ""
        val ingredients = intent.getStringExtra(RECIPE_INGREDIENT) ?: ""
        val procedure = intent.getStringExtra(RECIPE_STEP) ?: ""

//        viewModel.setRecipeId(recipeId)
//        setupComponents()
        bind(imageUrl, title, ingredients, procedure)
    }

    private fun bind(imageUrl: String, name: String, ingredients: String, procedure: String) {
        binding?.let { bind ->
            Glide.with(this).load(imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .into(bind.imageRecipe)
            bind.articleTitle.text = name
            bind.rvIngredients.adapter =
                UnorderListAdapter(
                    ingredients.split("--").map { ingredient -> UnorderList(ingredient) })
            bind.rvSteps.adapter =
                OrderListAdapter(
                    procedure.split("--").mapIndexed { index, step -> OrderList(index, step) })
        }
    }

    private fun setupComponents() {
        binding?.let {
//            viewModelObserver(it)
        }
    }

//    private fun viewModelObserver(bind: ActivityRecipeDetailBinding) {
//        viewModel.userState.observe(this) {
//            if (it is UserState.Success) {
//                Glide.with(this)
//                    .load(it.data.photoUrl)
//                    .placeholder(R.drawable.ic_person_circle)
//                    .into(bind.userPhotoProfile)
//            }
//        }
//        viewModel.getDetailRecipe().observe(this) {
//            Glide.with(this).load(it.imageUrl)
//                .placeholder(R.drawable.food_placeholder)
//                .into(bind.imageRecipe)
//            bind.articleTitle.text = it.name
//            bind.rvIngredients.adapter =
//                UnorderListAdapter(it.ingredients.map { ingredient -> UnorderList(ingredient) })
//            bind.rvSteps.adapter =
//                OrderListAdapter(it.procedure.mapIndexed { index, step -> OrderList(index, step) })
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}