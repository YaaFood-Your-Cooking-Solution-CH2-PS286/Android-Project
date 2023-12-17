package com.capstone.yafood.screen.articledetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.adapter.OrderList
import com.capstone.yafood.adapter.OrderListAdapter
import com.capstone.yafood.adapter.UnorderList
import com.capstone.yafood.adapter.UnorderListAdapter
import com.capstone.yafood.databinding.ActivityArticleDetailBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.ARTICLE_ID
import com.capstone.yafood.utils.ARTICLE_IMAGE
import com.capstone.yafood.utils.ARTICLE_INGREDIENT
import com.capstone.yafood.utils.ARTICLE_STEP
import com.capstone.yafood.utils.ARTICLE_TITLE

class ArticleDetailActivity : AppCompatActivity() {

    private var binding: ActivityArticleDetailBinding? = null
    private val viewModel by viewModels<ArticleDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val articleId = intent.getIntExtra(ARTICLE_ID, -1)
        val title = intent.getStringExtra(ARTICLE_TITLE) ?: ""
        val imageUrl = intent.getStringExtra(ARTICLE_IMAGE) ?: ""
        val ingredients = intent.getStringExtra(ARTICLE_INGREDIENT) ?: ""
        val procedure = intent.getStringExtra(ARTICLE_STEP) ?: ""
//            bind.likeCount.text = it.likeCount.toString()
//            bind.commentCount.text = it.commentCount.toString()
//            bind.articleDescription.text = it.description
//        viewModel.setArticleId(articleId)
//        setupComponents()
        bind(title, imageUrl, ingredients, procedure)
        binding?.icMoreVert?.setOnClickListener {
            showPopupMenu(it, articleId)
        }
    }

    private fun bind(imageUrl: String, name: String, ingredients: String, procedure: String) {
        binding?.let { bind ->
            Glide.with(this).load(imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .into(bind.articleImage)
            bind.articleTitle.text = name
            bind.rvIngredients.adapter =
                UnorderListAdapter(
                    ingredients.split("--").map { ingredient -> UnorderList(ingredient) })
            bind.rvSteps.adapter =
                OrderListAdapter(
                    procedure.split("--").mapIndexed { index, step -> OrderList(index, step) })
        }
    }

    private fun showPopupMenu(view: View, articleId: Int) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.article_detail_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.menu_delete_article -> {
                    deleteArticle(articleId)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun deleteArticle(articleId: Int) {
        viewModel.deleteArticle(articleId) { success ->
            if (success) {
                Toast.makeText(this, "Artikel Berhasil dihapus", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Gagal menghapus artikel", Toast.LENGTH_SHORT).show()
            }
        }
    }


//    private fun setupComponents() {
//        binding?.let {
////            viewModelObserver(it)
//        }
//    }
//
//    private fun viewModelObserver(bind: ActivityArticleDetailBinding) {
//        viewModel.user.observe(this) {
//            if (it is UserState.Success) {
//                Glide.with(this)
//                    .load(it.data.photoUrl)
//                    .placeholder(R.drawable.ic_person_circle)
//                    .into(bind.userPhotoProfile)
//            }
//        }
//        viewModel.getDetailArticle().observe(this) {
//            Glide.with(this).load(it.imageUrl)
//                .placeholder(R.drawable.food_placeholder)
//                .into(bind.articleImage)
//            bind.articleTitle.text = it.title
//            bind.likeCount.text = it.likeCount.toString()
//            bind.commentCount.text = it.commentCount.toString()
//            bind.articleDescription.text = it.description
//            bind.rvIngredients.adapter =
//                UnorderListAdapter(it.ingredients.map { ingredient -> UnorderList(ingredient) })
//            bind.rvSteps.adapter =
//                OrderListAdapter(it.procedure.mapIndexed { index, step -> OrderList(index, step) })
//
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}