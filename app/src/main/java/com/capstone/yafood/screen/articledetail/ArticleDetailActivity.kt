package com.capstone.yafood.screen.articledetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.OrderList
import com.capstone.yafood.adapter.OrderListAdapter
import com.capstone.yafood.adapter.UnorderList
import com.capstone.yafood.adapter.UnorderListAdapter
import com.capstone.yafood.databinding.ActivityArticleDetailBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.screen.auth.AuthActivity
import com.capstone.yafood.utils.ARTICLE_ID
import com.capstone.yafood.utils.ARTICLE_IMAGE
import com.capstone.yafood.utils.ARTICLE_INGREDIENT
import com.capstone.yafood.utils.ARTICLE_STEP
import com.capstone.yafood.utils.ARTICLE_TITLE
import com.capstone.yafood.utils.ARTICLE_USER_IMAGE
import com.capstone.yafood.utils.ARTICLE_USER_NAME
import com.capstone.yafood.utils.DELETE_ARTICLE_REQUEST_CODE
import com.capstone.yafood.utils.UserState
import com.google.android.material.snackbar.Snackbar

class ArticleDetailActivity : AppCompatActivity() {

    private var binding: ActivityArticleDetailBinding? = null
    private val viewModel by viewModels<ArticleDetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private var commentsFragment: CommentsFragment? = null

    private var userId = -1
    private var articleId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        articleId = intent.getIntExtra(ARTICLE_ID, -1)
        val title = intent.getStringExtra(ARTICLE_TITLE) ?: ""
        val imageUrl = intent.getStringExtra(ARTICLE_IMAGE) ?: ""
        val ingredients = intent.getStringExtra(ARTICLE_INGREDIENT) ?: ""
        val procedure = intent.getStringExtra(ARTICLE_STEP) ?: ""

        val userCreatedName = intent.getStringExtra(ARTICLE_USER_NAME) ?: ""
        val userCreatedImageUrl = intent.getStringExtra(ARTICLE_USER_IMAGE) ?: ""

        viewModel.setArticleId(articleId)
        setupContent(imageUrl, title, ingredients, procedure, userCreatedName, userCreatedImageUrl)
        binding?.let {
            viewModelObserver(it)
        }
    }

    private fun setupContent(
        imageUrl: String,
        name: String,
        ingredients: String,
        procedure: String,
        userCreatedName: String,
        userCreatedImageUrl: String
    ) {
        bind(
            imageUrl,
            name,
            ingredients.split("--"),
            procedure.split("--"),
            userCreatedName,
            userCreatedImageUrl
        )
    }

    private fun bind(
        imageUrl: String,
        name: String,
        ingredients: List<String>,
        procedure: List<String>,
        userCreatedName: String,
        userCreatedImageUrl: String
    ) {
        binding?.let { bind ->
            Glide.with(this).load(imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_placeholder)
                .into(bind.articleImage)
            Glide.with(this).load(userCreatedImageUrl).placeholder(R.drawable.ic_chef)
                .into(bind.userCreatorPhoto)
            bind.userName.text = userCreatedName

            bind.articleTitle.text = name
            bind.rvIngredients.adapter =
                UnorderListAdapter(
                    ingredients.map { ingredient -> UnorderList(ingredient) })
            bind.rvSteps.adapter =
                OrderListAdapter(
                    procedure.mapIndexed { index, step -> OrderList(index, step) })
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
                setResult(DELETE_ARTICLE_REQUEST_CODE)
                finish()
            } else {
                Toast.makeText(this, "Gagal menghapus artikel", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun viewModelObserver(bind: ActivityArticleDetailBinding) {
        viewModel.user.observe(this) {
            if (it is UserState.Success) {
                userId = it.data.id
                commentsFragment = CommentsFragment(articleId, it.data.photoUrl ?: "") { count ->
                    bind.commentTotal.text = count.toString()
                }
            }
            bind.btnComment.setOnClickListener { _ ->
                if (it is UserState.Success) {
                    commentsFragment?.show(supportFragmentManager, CommentsFragment.TAG)
                } else {
                    showLoginSuggestion()
                }
            }
        }

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Error -> Toast.makeText(this, state.errorMessage, Toast.LENGTH_SHORT)
                    .show()

                UiState.Loading -> {}
                is UiState.Success -> {
                    state.data.apply {
                        bind(
                            imageUrl,
                            title,
                            ingredients,
                            procedure,
                            userCreated.name,
                            userCreated.photoUrl ?: ""
                        )
                        binding?.articleDescription?.text = description
//                        Log.d("Test", "$userId : ${userCreated.id}")
                        if (userId > -1 && userId == userCreated.id) {
                            bind.moreMenu.setOnClickListener { view ->
                                showPopupMenu(view, id)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoginSuggestion() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            getString(R.string.no_login_will_comment),
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("Login") {
            // Arahkan pengguna ke Login Activity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}