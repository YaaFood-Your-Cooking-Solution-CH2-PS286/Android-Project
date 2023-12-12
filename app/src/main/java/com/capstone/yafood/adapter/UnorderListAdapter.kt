package com.capstone.yafood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.yafood.databinding.ItemOlBinding
import com.capstone.yafood.databinding.ItemUlBinding

class UnorderListAdapter(
    val listData: List<UnorderList>
) : RecyclerView.Adapter<UnorderListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUlBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemUlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.apply {
            binding.dataText.text = data.text
            data.icon?.let {
                binding.orderIcon.setImageResource(it)
            }
        }
    }
}

data class UnorderList(
    val text: String,
    val icon: Int? = null
)