package com.capstone.yafood.screen.search

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.GridArticleAdapter
import com.capstone.yafood.databinding.FragmentSearchBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.ListSpaceDecoration

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by activityViewModels<SearchViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.article.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Error -> {
                    binding.loadingBar.visibility = View.GONE
                    Toast.makeText(
                        requireActivity(), state.errorMessage, Toast.LENGTH_SHORT
                    ).show()
                }

                UiState.Loading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.rvArticles.visibility = View.GONE
                    val postY = binding.loadingBar.translationY
                    ObjectAnimator.ofFloat(
                        binding.loadingBar,
                        View.TRANSLATION_Y,
                        postY + 1000,
                        postY
                    )
                }

                is UiState.Success -> {
                    binding.loadingBar.visibility = View.GONE
                    state.data.let {
                        if (it.isNotEmpty()) {
                            binding.rvArticles.visibility = View.VISIBLE
                            binding.rvArticles.adapter = GridArticleAdapter(it)
                        } else {
                            binding.rvArticles.visibility = View.GONE
                            binding.errorMessage.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
        viewModel.query.observe(viewLifecycleOwner) {
//            binding.searchView.setQuery(it, false)
        }
    }


    private fun setupComponents() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) viewModel.searchArticles(query)
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText !== null) viewModel.postQuery(newText)
                return false
            }
        })
        binding.rvArticles.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            addItemDecoration(ListSpaceDecoration(16, 16))
        }

    }


}