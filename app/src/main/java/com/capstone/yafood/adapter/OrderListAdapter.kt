package com.capstone.yafood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.yafood.databinding.ItemOlBinding

class OrderListAdapter(
    val listData: List<OrderList>
) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemOlBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemOlBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.orderNumber.text = "${position + 1}."
            binding.dataText.text = listData[position].text
        }
    }
}

data class OrderList(
    val number: Int,
    val text: String
)