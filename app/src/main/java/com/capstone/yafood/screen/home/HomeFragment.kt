package com.capstone.yafood.screen.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.capstone.yafood.R
import com.capstone.yafood.databinding.FragmentHomeBinding
import com.capstone.yafood.screen.ViewModelFactory
import javax.sql.DataSource

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
    }

    private fun setupComponent() {
        binding?.let {
            viewmodelObserve(it)
        }
    }
}