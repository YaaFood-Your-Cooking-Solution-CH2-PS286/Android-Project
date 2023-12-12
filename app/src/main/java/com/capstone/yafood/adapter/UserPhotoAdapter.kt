package com.capstone.yafood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.data.entity.User
import com.capstone.yafood.databinding.PhotoUserBinding

class UserPhotoAdapter(
    private val users: List<User>
) : RecyclerView.Adapter<UserPhotoAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PhotoUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPhotoAdapter.ViewHolder =
        ViewHolder(
            PhotoUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: UserPhotoAdapter.ViewHolder, position: Int) {
        val user = users[position]
        Glide.with(holder.itemView).load(user.photoUrl).into(holder.binding.root)
    }

    override fun getItemCount(): Int = users.size
}