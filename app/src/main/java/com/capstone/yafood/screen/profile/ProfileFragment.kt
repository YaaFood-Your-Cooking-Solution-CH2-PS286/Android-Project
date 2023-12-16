package com.capstone.yafood.screen.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.UserArticleAdapter
import com.capstone.yafood.databinding.FragmentProfileBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.screen.auth.AuthActivity
import com.capstone.yafood.screen.createarticle.CreateArticleActivity
import com.capstone.yafood.utils.ListSpaceDecoration
import com.capstone.yafood.utils.UserState

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val viewModel by activityViewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComponent()
    }

    private fun setupComponent() {
        binding?.let {
            viewModelObserve(it)
            it.articlesRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                addItemDecoration(ListSpaceDecoration(verticalSpacing = 24))
            }
            it.userName.text = requireActivity().application.getString(R.string.profile)
            it.btnLogin.setOnClickListener {
                startActivity(Intent(requireActivity(), AuthActivity::class.java))
            }
            it.toCreateArticle.setOnClickListener {
                startActivity(Intent(requireActivity(), CreateArticleActivity::class.java))
            }
        }

    }

    private fun viewModelObserve(bind: FragmentProfileBinding) {
        viewModel.userData.observe(viewLifecycleOwner) {
            when (it) {
                UserState.Loading -> {}
                is UserState.Success -> {
                    bind.loginContainer.visibility = View.GONE
                    bind.apply {
                        headerText.text = it.data.email
                        userName.text = it.data.name
                        rank.text = "Pemasak Handal (${it.data.rankPoints})"
                    }
                    Glide.with(this)
                        .load(it.data.photoUrl)
                        .error(R.drawable.ic_person_circle)
                        .placeholder(requireActivity().getDrawable(R.drawable.ic_person_circle))
                        .into(bind.userPhotoProfile)
                }

                UserState.Unauthorized -> {
                    bind.loginContainer.visibility = View.VISIBLE
                    bind.fabAddArticle.visibility = View.GONE
                    bind.userName.text = requireActivity().application.getString(R.string.profile)
                }
            }
        }
        viewModel.userArticles.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Error -> TODO()
                UiState.Loading -> {

                }

                is UiState.Success -> {
                    if (it.data.isNotEmpty()) {
                        bind.articlesRecyclerView.adapter = UserArticleAdapter(it.data)
                        bind.notHaveArticleContainer.visibility = View.GONE
                        bind.fabAddArticle.visibility = View.VISIBLE
                    } else {
                        bind.notHaveArticleContainer.visibility = View.VISIBLE
                        bind.fabAddArticle.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}