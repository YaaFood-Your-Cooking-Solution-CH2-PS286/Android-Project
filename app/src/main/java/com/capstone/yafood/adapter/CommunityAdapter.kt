package com.capstone.yafood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.data.entity.Community
import com.capstone.yafood.databinding.ItemCommuntyBinding
import com.capstone.yafood.utils.ListSpaceDecoration
import com.capstone.yafood.utils.StackListDecoration

class CommunityAdapter(
    val listCommunities: List<Community>
) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemCommuntyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityAdapter.ViewHolder =
        ViewHolder(
            ItemCommuntyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: CommunityAdapter.ViewHolder, position: Int) {
        val community = listCommunities[position]

        holder.apply {
            binding.articleTitle.text = community.name
            Glide.with(itemView).load(community.photoUrl).into(binding.articleImage)
        }
        holder.binding.rvUserPhoto.apply {
            layoutManager =
                LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(StackListDecoration(-32))
            adapter = UserPhotoAdapter(community.users)
        }
    }

    override fun getItemCount(): Int = listCommunities.size
}