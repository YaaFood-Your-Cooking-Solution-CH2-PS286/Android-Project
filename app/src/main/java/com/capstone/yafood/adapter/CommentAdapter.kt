package com.capstone.yafood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.data.entity.Comment
import com.capstone.yafood.databinding.ItemCommentBinding

class CommentAdapter(
    private val listComment: List<Comment>
) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            binding.userName.text = comment.user.name
            Glide.with(binding.root).load(comment.user.photoUrl).placeholder(R.drawable.ic_chef)
                .error(R.drawable.ic_chef).into(binding.userImage)
            binding.comment.text = comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listComment.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listComment[position])
    }


}