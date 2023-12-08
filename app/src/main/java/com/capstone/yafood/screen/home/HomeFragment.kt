package com.capstone.yafood.screen.home

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
import com.capstone.yafood.adapter.ArticleAdapter
import com.capstone.yafood.databinding.FragmentHomeBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.screen.snapcook.SnapCookActivity
import com.capstone.yafood.utils.ListSpaceDecoration

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
        viewModel.userData.observe(viewLifecycleOwner) {
            bind.userName.text = it.username

            if (it.photoUrl != null) {
                Glide.with(this)
                    .load(it.photoUrl)
                    .error(R.drawable.ic_person_circle)
                    .placeholder(requireActivity().getDrawable(R.drawable.ic_person_circle))
                    .into(bind.userPhotoProfile)
            }
        }

        viewModel.getNewestArticles().observe(viewLifecycleOwner) {
            bind.rvArticles.adapter = ArticleAdapter(it)
        }
    }


    private fun setupComponent() {
        binding?.let {
            viewmodelObserve(it)
            it.btnOpenCamera.setOnClickListener {
                startActivity(Intent(requireActivity(), SnapCookActivity::class.java))
            }
            it.rvArticles.apply {
                layoutManager =
                    LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(ListSpaceDecoration(24))
            }
        }
    }
}