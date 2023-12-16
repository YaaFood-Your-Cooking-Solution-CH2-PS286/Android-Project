package com.capstone.yafood.screen.articledetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.adapter.OrderList
import com.capstone.yafood.adapter.OrderListAdapter
import com.capstone.yafood.adapter.UnorderList
import com.capstone.yafood.adapter.UnorderListAdapter
import com.capstone.yafood.databinding.ActivityArticleDetailBinding
import com.capstone.yafood.databinding.ActivityRecipeDetailBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.screen.recipedetail.RecipeDetailViewModel
import com.capstone.yafood.utils.RECIPE_ID

class ArticleDetailActivity : AppCompatActivity() {

    private var binding: ActivityArticleDetailBinding? = null
    private val viewModel by viewModels<ArticleDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val articleId = intent.getIntExtra(RECIPE_ID, -1)
        viewModel.setArticleId(articleId)
        setupComponents()
    }

    private fun setupComponents() {
        binding?.let {
            viewModelObserver(it)
        }
    }

    private fun viewModelObserver(bind: ActivityArticleDetailBinding) {
        viewModel.user.observe(this) {
            Glide.with(this)
                .load(it.photoUrl)
                .placeholder(R.drawable.ic_person_circle)
                .into(bind.userPhotoProfile)
        }
        viewModel.getDetailArticle().observe(this) {
            Glide.with(this).load(it.imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .into(bind.articleImage)
            bind.articleTitle.text = it.title
            bind.likeCount.text = it.likeCount.toString()
            bind.commentCount.text = it.commentCount.toString()
            bind.articleDescription.text = it.description
            bind.rvIngredients.adapter =
                UnorderListAdapter(it.ingredients.map { ingredient -> UnorderList(ingredient) })
            bind.rvSteps.adapter =
                OrderListAdapter(it.procedure.mapIndexed { index, step -> OrderList(index, step) })

        }
    }
}