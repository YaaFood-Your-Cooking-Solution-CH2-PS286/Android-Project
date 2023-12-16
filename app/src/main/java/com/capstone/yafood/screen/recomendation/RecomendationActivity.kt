package com.capstone.yafood.screen.recomendation

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.IngredientAdapter
import com.capstone.yafood.adapter.RecipeRecomendationAdapter
import com.capstone.yafood.databinding.ActivityRecomendationBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.ListSpaceDecoration
import com.capstone.yafood.utils.RESULT_SNAP
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class RecomendationActivity : AppCompatActivity() {
    private var searchAnim: ObjectAnimator? = null
    private lateinit var binding: ActivityRecomendationBinding
    private val viewModel by viewModels<RecomendationViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var addIngredientsFragment: AddIngredientsFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        binding.btnBack.setOnClickListener { finish() }
        val image = intent.getStringExtra(RESULT_SNAP)
        image?.let {
            viewModel.getRecomendationByImage(it)
        }
        setupComponents()
        viewModelObserve()
    }

    private fun setupComponents() {
        addIngredientsFragment = AddIngredientsFragment(viewModel::updateListIngredients)

        val layoutManager = FlexboxLayoutManager(this)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        binding.rvIngredients.apply {
            this.layoutManager = layoutManager
            addItemDecoration(ListSpaceDecoration(8, 8))
        }

        binding.rvRecipeRecomendation.apply {
            this.layoutManager = LinearLayoutManager(application)
            addItemDecoration(ListSpaceDecoration(verticalSpacing = 24))
        }

        binding.btnBack.setOnClickListener { finish() }
        binding.btnRetakePhoto.setOnClickListener { finish() }
        binding.btnManualInput.setOnClickListener {
            setVisibleErrorView(false)
            handleAddIngredient()
        }
    }

    private fun viewModelObserve() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                UiState.Loading -> {
                    binding.loadingContainer.visibility = View.VISIBLE
                    setVisibleErrorView(false)
                    binding.contentContainer.visibility = View.INVISIBLE
                    setSearchView(true)
                }

                is UiState.Error -> {
                    setSearchView(false)
                    setVisibleErrorView(true)
                    binding.contentContainer.visibility = View.INVISIBLE

                    binding.errorMessage.text =
                        getString(if (state.errorMessage == "timeout") R.string.timeout_get_recomendation else R.string.error_get_recomendation)
                }

                is UiState.Success -> {
                    setSearchView(false)
                    setVisibleErrorView(false)
                    binding.contentContainer.visibility = View.VISIBLE
                    binding.rvIngredients.adapter =
                        IngredientAdapter(state.data.ingredientDetected, ::handleAddIngredient)
                    binding.rvRecipeRecomendation.adapter =
                        RecipeRecomendationAdapter(state.data.recipe)
                    initAction()
                }
            }
        }
    }

    private fun handleAddIngredient() {
        addIngredientsFragment.show(supportFragmentManager, AddIngredientsFragment.TAG)
    }

    private fun setVisibleErrorView(isVisible: Boolean) {
        binding.errorContainer.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun initAction() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val ingredient = (viewHolder as IngredientAdapter.ItemViewHolder).getIngredient
                viewModel.deleteIngredient(ingredient)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.rvIngredients)
    }

    private fun setSearchView(isVisible: Boolean) {
        if (isVisible) {
            val postY = binding.searchAnim.translationY
            searchAnim = ObjectAnimator.ofFloat(
                binding.searchAnim,
                View.TRANSLATION_Y,
                postY,
                postY - 70
            ).apply {
                duration = 800
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
                interpolator = AccelerateDecelerateInterpolator()
                start()
            }
            binding.loadingContainer.visibility = View.VISIBLE
        } else {
            searchAnim?.let { it.cancel() }
            binding.loadingContainer.visibility = View.GONE
        }
    }
//    private fun setSearchAnim() {
//        val postX = binding.searchAnim.translationX
//        val translateX = ObjectAnimator.ofFloat(
//            binding.searchAnim,
//            View.TRANSLATION_X,
//            postX - 30,
//            postX - 70,
//            postX,
//            postX - 30
//        ).apply {
//            duration = 1500
//            repeatCount = ObjectAnimator.INFINITE
//        }
//
//        val postY = binding.searchAnim.translationY
//        val translateY = ObjectAnimator.ofFloat(
//            binding.searchAnim,
//            View.TRANSLATION_Y,
//            postY,
//            postY - 70,
//            postY - 70,
//            postY
//        ).apply {
//            duration = 1500
//            repeatCount = ObjectAnimator.INFINITE
//        }
//
//        AnimatorSet().apply {
//            playTogether(translateX, translateY)
//            start()
//        }
//    }


}