package com.capstone.yafood.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.databinding.ItemRecomendationRecipeBinding
import com.capstone.yafood.screen.recipedetail.RecipeDetailActivity
import com.capstone.yafood.utils.RECIPE_ID

class RecipeRecomendationAdapter(
    private val listRecipe: List<Recipe>
) : RecyclerView.Adapter<RecipeRecomendationAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRecomendationRecipeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRecomendationRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listRecipe.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = listRecipe[position]
        holder.apply {
            binding.recipeIngredients.text = recipe.ingredients.joinToString(", ")
            Glide.with(itemView.context).load(recipe.imageUrl).into(binding.recipeImage)
            binding.recipeTitle.text = recipe.name
            itemView.setOnClickListener {
                val toDetail = Intent(itemView.context, RecipeDetailActivity::class.java)
                toDetail.putExtra(RECIPE_ID, recipe.id)
                itemView.context.startActivity(toDetail)
            }
        }
    }
}