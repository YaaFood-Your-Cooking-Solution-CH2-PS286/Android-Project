package com.capstone.yafood.screen.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.databinding.FragmentRegisterBinding
import com.capstone.yafood.screen.ViewModelFactory
import java.lang.Exception

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel by activityViewModels<AuthViewModel> {
        ViewModelFactory.getInstance(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
        viewModelObserver()
    }

    private fun viewModelObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Error -> {
//                    Toast.makeText(requireActivity(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.loadingContainer.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                    try {
                        uiState.errorMessage.split("|").forEach {
                            val error = it.split(";")
                            when (error[0]) {
                                "name" -> binding.inputName.error = error[1]
                                "email" -> binding.inputEmail.error = error[1]
                                "password" -> binding.inputPassword.error = error[1]
                                "confPassword" -> binding.inputConfPassword.error = error[1]
                                else -> {}
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(
                            requireActivity(),
                            requireActivity().getString(R.string.failed_register),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Toast.makeText(
                        requireActivity(),
                        requireActivity().getString(R.string.failed_register),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UiState.Loading -> {
                    binding.loadingContainer.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.VISIBLE
                    loadingAnim()
                }

                is UiState.Success -> {
                    Toast.makeText(requireActivity(), uiState.data, Toast.LENGTH_SHORT).show()
                    binding.loadingContainer.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                }

                else -> {

                }
            }
        }

        viewModel.formRegister.observe(requireActivity()) {
            binding.btnRegister.isEnabled =
                (it.name.isNotEmpty() && it.email.isNotEmpty() && it.password.isNotEmpty() && it.confPassword.isNotEmpty())
        }
    }

    private fun loadingAnim() {
        val postY = binding.loadingBar.translationY
        ObjectAnimator.ofFloat(binding.loadingBar, View.TRANSLATION_Y, postY + 1000, postY)
            .apply {
                duration = 200
                repeatCount = 0
            }.start()
    }

    private fun setupComponents() {
        binding.btnRegister.setOnClickListener {
            viewModel.submitRegister()
        }
        binding.inputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.setFormRegister(inputName = s.toString())
            }
        })

        binding.inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.setFormRegister(inputEmail = s.toString())
            }
        })

        binding.inputPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.setFormRegister(inputPassword = s.toString())
            }
        })

        binding.inputConfPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.setFormRegister(inputConfPassword = s.toString())
            }
        })
    }

}