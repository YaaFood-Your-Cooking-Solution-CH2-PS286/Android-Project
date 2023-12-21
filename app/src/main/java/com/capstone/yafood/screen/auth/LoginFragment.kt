package com.capstone.yafood.screen.auth

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.capstone.yafood.MainActivity
import com.capstone.yafood.UiState
import com.capstone.yafood.databinding.FragmentLoginBinding
import com.capstone.yafood.screen.ViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by activityViewModels<AuthViewModel> {
        ViewModelFactory.getInstance(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
                    Toast.makeText(
                        requireActivity(),
                        uiState.errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.loadingContainer.visibility = View.GONE
                    binding.loadingBar.visibility = View.GONE
                }

                UiState.Loading -> {
                    binding.loadingContainer.visibility = View.VISIBLE
                    binding.loadingBar.visibility = View.VISIBLE
                    loadingAnim()
                }

                is UiState.Success -> {
                    Toast.makeText(requireActivity(), uiState.data, Toast.LENGTH_SHORT).show()
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setupComponents() {
        binding.btnLogin.setOnClickListener {
            viewModel.submitLogin()
        }

        binding.inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                viewModel.setFormLogin(inputEmail = s.toString())
            }
        })

        binding.inputPassword.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    viewModel.setFormLogin(inputPassword = s.toString())
                }
            })
            setOnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.btnLogin.performClick()
                }

                true

            }
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
}