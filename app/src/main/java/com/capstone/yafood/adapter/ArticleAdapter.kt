package com.capstone.yafood.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.databinding.ItemArticleBinding

class ArticleAdapter(
    private val listArticles: List<Article>
) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder =
        ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = listArticles.size

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val article = listArticles[position]
        Log.d("GetDailyArticles", listArticles.toString())
        holder.apply {
            binding.username.text = article.userCreated.name
            Glide.with(itemView.context)
                .load(article.userCreated.photoUrl)
                .error(R.drawable.ic_person_circle)
                .placeholder(itemView.context.getDrawable(R.drawable.ic_person_circle))
                .into(binding.userPhotoProfile)

            Glide.with(itemView.context).load(article.imageUrl).into(binding.articleImage)
            binding.articleTitle.text = article.title
        }
    }
}