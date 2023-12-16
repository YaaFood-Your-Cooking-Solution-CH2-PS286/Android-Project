package com.capstone.yafood.screen.home

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.adapter.ArticleAdapter
import com.capstone.yafood.adapter.CommunityAdapter
import com.capstone.yafood.databinding.FragmentHomeBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.screen.snapcook.SnapCookActivity
import com.capstone.yafood.utils.ListSpaceDecoration
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
                        .error(R.drawable.ic_person_circle)
                        .placeholder(requireActivity().getDrawable(R.drawable.ic_person_circle))
                        .into(bind.userPhotoProfile)
                    bind.userName.text = it.data.name

                    if (it.data.photoUrl != null) {
                        Glide.with(this)
                            .load(it.data.photoUrl)
                            .error(R.drawable.ic_person_circle)
                            .placeholder(requireActivity().getDrawable(R.drawable.ic_person_circle))
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

        viewModel.getRecipeIdea().observe(viewLifecycleOwner) {
            //set ingredient
            Glide.with(this).load(it.ingredient.imageUrl).into(bind.ingredientImage)
            bind.ingredientTitle.text =
                requireActivity().resources.getString(R.string.recipe_of, it.ingredient.name)

            if (it.recipes.isNotEmpty()) {
                val firstRecipe = it.recipes[0] ?: null
                val secondRecipe = it.recipes[1] ?: null
                //set first recipe
                firstRecipe?.let {
                    Glide.with(this).load(firstRecipe.imageUrl).into(bind.imageOfFirstRecipe)
                    bind.titleOfFirstRecipe.text = firstRecipe.name
                }
                //set second recipe
                secondRecipe?.let {
                    Glide.with(this).load(secondRecipe.imageUrl).into(bind.imageOfSecondRecipe)
                    bind.titleOfSecondRecipe.text = secondRecipe.name
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