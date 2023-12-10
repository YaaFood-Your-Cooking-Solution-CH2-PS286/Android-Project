package com.capstone.yafood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.data.entity.Ingredient
import com.capstone.yafood.databinding.ButtonAddIngredientBinding
import com.capstone.yafood.databinding.ItemIngredientBinding

class IngredientAdapter(
    private val listIngredients: List<Ingredient>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ItemViewHolder(val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ButtonAddViewHolder(val binding: ButtonAddIngredientBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            listIngredients.size -> R.layout.button_add_ingredient
            else -> R.layout.item_ingredient
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == R.layout.button_add_ingredient) ButtonAddViewHolder(
            ButtonAddIngredientBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
        else ItemViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listIngredients.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            R.layout.item_ingredient -> {
                (holder as ItemViewHolder).apply {
                    val ingredient = listIngredients[position]
                    Glide.with(holder.itemView).load(ingredient.imageUrl).into(binding.image)
                    binding.label.text = ingredient.name
                }
            }

            R.layout.button_add_ingredient -> {
                (holder as ButtonAddViewHolder).apply {
                    itemView.setOnClickListener {
                        Toast.makeText(itemView.context, "Test Tamba", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}