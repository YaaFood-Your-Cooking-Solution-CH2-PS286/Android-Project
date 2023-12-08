package com.capstone.yafood.screen.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.adapter.UserArticleAdapter
import com.capstone.yafood.databinding.FragmentHomeBinding
import com.capstone.yafood.databinding.FragmentProfileBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.ListSpaceDecoration

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
            viewmodelObserve(it)
            it.articlesRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                addItemDecoration(ListSpaceDecoration(verticalSpacing = 24))
            }
        }

    }

    private fun viewmodelObserve(bind: FragmentProfileBinding) {
        viewModel.userData.observe(viewLifecycleOwner) {
            bind.apply {
                headerText.text = it.username
                userName.text = it.name
                rank.text = "Pemasak Handal (${it.rankPoints})"
            }
            Glide.with(this)
                .load(it.photoUrl)
                .error(R.drawable.ic_person_circle)
                .placeholder(requireActivity().getDrawable(R.drawable.ic_person_circle))
                .into(bind.userPhotoProfile)
        }

        viewModel.getUserArticles().observe(viewLifecycleOwner) {
            bind.articlesRecyclerView.adapter = UserArticleAdapter(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}