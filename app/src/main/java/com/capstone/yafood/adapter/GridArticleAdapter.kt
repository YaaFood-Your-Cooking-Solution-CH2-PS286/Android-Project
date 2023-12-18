package com.capstone.yafood.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.databinding.ItemGridArticleBinding
import com.capstone.yafood.screen.articledetail.ArticleDetailActivity
import com.capstone.yafood.utils.ARTICLE_ID
import com.capstone.yafood.utils.ARTICLE_IMAGE
import com.capstone.yafood.utils.ARTICLE_INGREDIENT
import com.capstone.yafood.utils.ARTICLE_STEP
import com.capstone.yafood.utils.ARTICLE_TITLE
import com.capstone.yafood.utils.ARTICLE_USER_ID
import com.capstone.yafood.utils.ARTICLE_USER_IMAGE
import com.capstone.yafood.utils.ARTICLE_USER_NAME

class GridArticleAdapter(
    private val listArticles: List<Article>
) : RecyclerView.Adapter<GridArticleAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemGridArticleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GridArticleAdapter.ViewHolder =
        ViewHolder(
            ItemGridArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = listArticles.size

    override fun onBindViewHolder(holder: GridArticleAdapter.ViewHolder, position: Int) {
        val article = listArticles[position]
        Log.d("GetDailyArticles", listArticles.toString())
        holder.apply {
            binding.username.text = article.userCreated.name
            Glide.with(itemView.context)
                .load(article.userCreated.photoUrl)
                .error(R.drawable.ic_chef)
                .placeholder(itemView.context.getDrawable(R.drawable.ic_chef))
                .into(binding.userPhotoProfile)

            Glide.with(itemView.context).load(article.imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_placeholder)
                .into(binding.articleImage)
            binding.articleTitle.text = article.title
            itemView.setOnClickListener {
                val toDetail = Intent(itemView.context, ArticleDetailActivity::class.java)
                toDetail.apply {
                    putExtra(ARTICLE_ID, article.id)
                    putExtra(ARTICLE_TITLE, article.title)
                    putExtra(ARTICLE_IMAGE, article.imageUrl)
                    putExtra(ARTICLE_INGREDIENT, article.ingredients.joinToString("--"))
                    putExtra(ARTICLE_STEP, article.procedure.joinToString("--"))
                    putExtra(ARTICLE_USER_ID, article.userCreated.id)
                    putExtra(ARTICLE_USER_NAME, article.userCreated.name)
                    putExtra(ARTICLE_USER_IMAGE, article.userCreated.photoUrl)
                }
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.articleImage, "image"),
                )
                itemView.context.startActivity(toDetail, optionsCompat.toBundle())
            }
        }
    }
}