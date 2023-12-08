package com.capstone.yafood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.data.entity.Article
import com.capstone.yafood.databinding.ItemUserArticleBinding

class UserArticleAdapter(
    private val listUserArticle: List<Article>
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
        }
    }

    override fun getItemCount(): Int = listUserArticle.size
}