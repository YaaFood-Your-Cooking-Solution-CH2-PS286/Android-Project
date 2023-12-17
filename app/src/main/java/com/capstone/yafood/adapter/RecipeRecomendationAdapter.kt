package com.capstone.yafood.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.databinding.ItemRecomendationRecipeBinding
import com.capstone.yafood.screen.recipedetail.RecipeDetailActivity
import com.capstone.yafood.utils.RECIPE_ID
import com.capstone.yafood.utils.RECIPE_IMAGE
import com.capstone.yafood.utils.RECIPE_INGREDIENT
import com.capstone.yafood.utils.RECIPE_STEP
import com.capstone.yafood.utils.RECIPE_TITLE

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
                toDetail.apply {
                    putExtra(RECIPE_ID, recipe.id)
                    putExtra(RECIPE_IMAGE, recipe.imageUrl)
                    putExtra(RECIPE_TITLE, recipe.name)
                    putExtra(RECIPE_INGREDIENT, recipe.ingredients.joinToString("--"))
                    putExtra(RECIPE_STEP, recipe.procedure.joinToString("--"))
                }

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.recipeImage, "image"),
                        Pair(binding.recipeTitle, "title")
                    )
                itemView.context.startActivity(toDetail, optionsCompat.toBundle())
            }
        }
    }
}