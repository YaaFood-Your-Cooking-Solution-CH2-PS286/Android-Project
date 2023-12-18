package com.capstone.yafood.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.databinding.ItemUserArticleBinding
import com.capstone.yafood.screen.articledetail.ArticleDetailActivity
import com.capstone.yafood.utils.ARTICLE_ID
import com.capstone.yafood.utils.ARTICLE_IMAGE
import com.capstone.yafood.utils.ARTICLE_INGREDIENT
import com.capstone.yafood.utils.ARTICLE_STEP
import com.capstone.yafood.utils.ARTICLE_TITLE
import com.capstone.yafood.utils.ARTICLE_USER_ID
import com.capstone.yafood.utils.ARTICLE_USER_IMAGE
import com.capstone.yafood.utils.ARTICLE_USER_NAME
import kotlin.reflect.KProperty0

class UserArticleAdapter(
    private val listUserArticle: List<Article>,
    private val startActivityResult: ActivityResultLauncher<Intent>,
) : RecyclerView.Adapter<UserArticleAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemUserArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserArticleAdapter.ViewHolder = ViewHolder(
        ItemUserArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: UserArticleAdapter.ViewHolder, position: Int) {
        val userArticle = listUserArticle[position]

        holder.apply {
            binding.likeCount.text = userArticle.likeCount.toString()
            binding.commentCount.text = userArticle.commentCount.toString()
            binding.articleTitle.text = userArticle.title
            Glide.with(holder.itemView).load(userArticle.imageUrl).into(binding.articleImage)
            itemView.setOnClickListener {
                val toDetail = Intent(itemView.context, ArticleDetailActivity::class.java)
                toDetail.apply {
                    putExtra(ARTICLE_ID, userArticle.id)
                    putExtra(ARTICLE_TITLE, userArticle.title)
                    putExtra(ARTICLE_IMAGE, userArticle.imageUrl)
                    putExtra(ARTICLE_INGREDIENT, userArticle.ingredients.joinToString("--"))
                    putExtra(ARTICLE_STEP, userArticle.procedure.joinToString("--"))
                    putExtra(ARTICLE_USER_ID, userArticle.userCreated.id)
                    putExtra(ARTICLE_USER_NAME, userArticle.userCreated.name)
                    putExtra(ARTICLE_USER_IMAGE, userArticle.userCreated.photoUrl)
                }
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.articleImage, "image"),
                )
                startActivityResult.launch(toDetail, optionsCompat)
//                itemView.context.startActivity(toDetail, optionsCompat.toBundle())
            }
        }
    }

    override fun getItemCount(): Int = listUserArticle.size
}