package com.capstone.yafood.screen.home

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.ArticleAdapter
import com.capstone.yafood.adapter.CommunityAdapter
import com.capstone.yafood.data.entity.Recipe
import com.capstone.yafood.databinding.FragmentHomeBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.screen.recipedetail.RecipeDetailActivity
import com.capstone.yafood.screen.snapcook.SnapCookActivity
import com.capstone.yafood.utils.ListSpaceDecoration
import com.capstone.yafood.utils.RECIPE_ID
import com.capstone.yafood.utils.RECIPE_IMAGE
import com.capstone.yafood.utils.RECIPE_INGREDIENT
import com.capstone.yafood.utils.RECIPE_STEP
import com.capstone.yafood.utils.RECIPE_TITLE
import com.capstone.yafood.utils.UserState

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponent()
    }

    private fun viewmodelObserve(bind: FragmentHomeBinding) {
        viewModel.userState.observe(viewLifecycleOwner) {
            when (it) {
                UserState.Loading -> {
                    bind.loadingProfile.visibility = View.VISIBLE
                }

                is UserState.Success -> {
                    bind.loadingProfile.visibility = View.GONE
                    bind.userInfo.visibility = View.VISIBLE
                    bind.appLogo.visibility = View.GONE

                    bind.userName.text = it.data.email

                    Glide.with(this)
                        .load(it.data.photoUrl ?: "")
                        .error(R.drawable.ic_chef)
                        .placeholder(requireActivity().getDrawable(R.drawable.ic_chef))
                        .into(bind.userPhotoProfile)
                    bind.userName.text = it.data.name

                    if (it.data.photoUrl != null) {
                        Glide.with(this)
                            .load(it.data.photoUrl)
                            .error(R.drawable.ic_chef)
                            .placeholder(requireActivity().getDrawable(R.drawable.ic_chef))
                            .into(bind.userPhotoProfile)
                    }
                }

                UserState.Unauthorized -> {
                    bind.loadingProfile.visibility = View.GONE
                    bind.userInfo.visibility = View.GONE
                    bind.appLogo.visibility = View.VISIBLE
                }
            }
        }

        viewModel.recipeIdea.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Error -> {
                    bind.recipeIdeaLoading.visibility = View.GONE
                    bind.recipeIdeaContainer.visibility = View.GONE
                }

                UiState.Loading -> {
                    bind.recipeIdeaLoading.visibility = View.VISIBLE
                    bind.recipeIdeaContainer.visibility = View.GONE
                }

                is UiState.Success -> {
                    bind.recipeIdeaContainer.visibility = View.VISIBLE
                    bind.recipeIdeaLoading.visibility = View.GONE
                    state.data.let {
                        //set ingredient
                        Glide.with(this).load(it.ingredient.imageUrl).into(bind.ingredientImage)
                        bind.ingredientTitle.text =
                            requireActivity().resources.getString(
                                R.string.recipe_of,
                                it.ingredient.name
                            )

                        if (it.recipes.isNotEmpty()) {
                            val firstRecipe = it.recipes[0] ?: null
                            val secondRecipe = it.recipes[1] ?: null
                            //set first recipe
                            firstRecipe?.let { recipe ->
                                Glide.with(this).load(recipe.imageUrl).into(bind.imageOfFirstRecipe)
                                bind.titleOfFirstRecipe.text = recipe.name

                                bind.recipeFirstItem.setOnClickListener { _ ->
                                    val toDetail =
                                        Intent(requireActivity(), RecipeDetailActivity::class.java)
                                    toDetail.apply {
                                        putExtra(RECIPE_ID, recipe.id)
                                        putExtra(RECIPE_IMAGE, recipe.imageUrl)
                                        putExtra(RECIPE_TITLE, recipe.name)
                                        putExtra(
                                            RECIPE_INGREDIENT,
                                            recipe.ingredients.joinToString("--")
                                        )
                                        putExtra(RECIPE_STEP, recipe.procedure.joinToString("--"))
                                    }

                                    val optionsCompat: ActivityOptionsCompat =
                                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            requireActivity(),
                                            Pair(bind.imageOfFirstRecipe, "image"),
                                        )
                                    requireActivity().startActivity(
                                        toDetail,
                                        optionsCompat.toBundle()
                                    )
                                }
                            }
                            //set second recipe
                            secondRecipe?.let { recipe ->
                                Glide.with(this).load(secondRecipe.imageUrl)
                                    .into(bind.imageOfSecondRecipe)
                                bind.titleOfSecondRecipe.text = secondRecipe.name

                                bind.recipeSecondItem.setOnClickListener { _ ->
                                    val toDetail =
                                        Intent(requireActivity(), RecipeDetailActivity::class.java)
                                    toDetail.apply {
                                        putExtra(RECIPE_ID, recipe.id)
                                        putExtra(RECIPE_IMAGE, recipe.imageUrl)
                                        putExtra(RECIPE_TITLE, recipe.name)
                                        putExtra(
                                            RECIPE_INGREDIENT,
                                            recipe.ingredients.joinToString("--")
                                        )
                                        putExtra(RECIPE_STEP, recipe.procedure.joinToString("--"))
                                    }

                                    val optionsCompat: ActivityOptionsCompat =
                                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            requireActivity(),
                                            Pair(bind.imageOfSecondRecipe, "image"),
                                        )
                                    requireActivity().startActivity(
                                        toDetail,
                                        optionsCompat.toBundle()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        viewModel.getNewestArticles().observe(viewLifecycleOwner) {
            bind.rvArticles.adapter = ArticleAdapter(it)
        }

        viewModel.getCommunities().observe(viewLifecycleOwner) {
            bind.rvCommunities.adapter = CommunityAdapter(it)
        }
    }


    private fun setupComponent() {
        binding?.let {
            viewmodelObserve(it)
            it.rvArticles.apply {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(ListSpaceDecoration(24))
            }

            it.rvCommunities.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                addItemDecoration(ListSpaceDecoration(verticalSpacing = 16))
            }
        }
    }
}