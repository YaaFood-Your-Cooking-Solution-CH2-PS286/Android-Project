package com.capstone.yafood.screen.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
import com.capstone.yafood.screen.settings.SettingsActivity
import com.capstone.yafood.utils.DELETE_ARTICLE_REQUEST_CODE
import com.capstone.yafood.utils.ListSpaceDecoration
import com.capstone.yafood.utils.USER_NAME
import com.capstone.yafood.utils.UserState

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private val viewModel by activityViewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    private val startDetailActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            Log.d("ResultTest", result.resultCode.toString())
            if (result.resultCode == DELETE_ARTICLE_REQUEST_CODE) {
                viewModel.getArticles()
            }
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
                toCreateArticleActivity()
            }
            it.fabAddArticle.setOnClickListener {
                toCreateArticleActivity()
            }
        }

    }

    private fun toCreateArticleActivity() {
        startActivity(Intent(requireActivity(), CreateArticleActivity::class.java))
    }

    private fun viewModelObserve(bind: FragmentProfileBinding) {
        viewModel.userData.observe(viewLifecycleOwner) {
            when (it) {
                UserState.Loading -> {
                    bind.settingButton.visibility = View.GONE
                }

                is UserState.Success -> {
                    bind.loginContainer.visibility = View.GONE
                    bind.fabAddArticle.visibility = View.VISIBLE
                    bind.settingButton.visibility = View.VISIBLE
                    bind.apply {
                        headerText.text = it.data.email
                        userName.text = it.data.name
                        rank.text = "Pemasak Handal (${it.data.rankPoints})"
                    }

                    bind.settingButton.setOnClickListener { _ ->
                        val toSettings = Intent(requireActivity(), SettingsActivity::class.java)
                        toSettings.putExtra(USER_NAME, it.data.name)
                        toSettings.putExtra(USER_NAME, it.data.email)
                        startActivity(toSettings)
                    }

                    Glide.with(this)
                        .load(it.data.photoUrl)
                        .error(R.drawable.ic_chef)
                        .placeholder(requireActivity().getDrawable(R.drawable.ic_chef))
                        .into(bind.userPhotoProfile)
                }

                UserState.Unauthorized -> {
                    bind.settingButton.visibility = View.GONE
                    bind.loginContainer.visibility = View.VISIBLE
                    bind.fabAddArticle.visibility = View.GONE
                    bind.userName.text = requireActivity().application.getString(R.string.profile)
                }
            }
        }
        viewModel.userArticles.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Error -> {
                    bind.articleLoading.visibility = View.GONE
                }

                UiState.Loading -> {
                    bind.articleLoading.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    bind.articleLoading.visibility = View.GONE
                    if (it.data.isNotEmpty()) {
                        bind.articlesRecyclerView.adapter =
                            UserArticleAdapter(it.data, startDetailActivityForResult)
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