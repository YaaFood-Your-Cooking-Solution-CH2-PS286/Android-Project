package com.capstone.yafood.screen.articledetail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.yafood.R
import com.capstone.yafood.UiState
import com.capstone.yafood.adapter.CommentAdapter
import com.capstone.yafood.databinding.FragmentCommentBinding
import com.capstone.yafood.utils.ListSpaceDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentsFragment(
    private val articleId: Int,
    private val userImageUrl: String,
    private val setCommentCount: (Int) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCommentBinding
    private val viewModel by activityViewModels<CommentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        viewModel.getComment(articleId)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupComponents()

    }

    private fun setupComponents() {
        Glide.with(this).load(userImageUrl).placeholder(R.drawable.ic_chef)
            .error(R.drawable.ic_chef).into(binding.userPhotoProfile)
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            addItemDecoration(ListSpaceDecoration(verticalSpacing = 24))
        }
        binding.btnSubmitComment.setOnClickListener {
            viewModel.submitComment(articleId)
        }
        binding.commentInput.apply {
            setText(viewModel.commentInput.value ?: "")
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
                    viewModel.setCommentInput(s?.toString() ?: "")
                }
            })

            setOnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.btnSubmitComment.performClick()
                }

                true

            }
        }
        viewModel.apply {
            uiState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is UiState.Error -> {
                        binding.loadingBar.visibility = View.GONE
                        binding.errorMessage.apply {
                            visibility = View.VISIBLE
                            text = state.errorMessage
                        }
                    }

                    UiState.Loading -> {
                        binding.loadingBar.visibility = View.VISIBLE
                        binding.errorMessage.visibility = View.GONE
                    }

                    is UiState.Success -> {
                        binding.loadingBar.visibility = View.GONE
                        binding.errorMessage.apply {
                            visibility = if (state.data.isEmpty()) View.VISIBLE else View.GONE
                            text = "Belum ada komentar pada artikel ini"
                        }
                        setCommentCount(state.data.size)
                        binding.rvComments.adapter = CommentAdapter(state.data)
                    }
                }
            }

            isLoadingSubmit.observe(viewLifecycleOwner) {
                binding.loadingSubmitComment.visibility = if (it) View.VISIBLE else View.GONE
                binding.btnSubmitComment.visibility = if (!it) View.VISIBLE else View.GONE
            }

            isSubmitSuccess.observe(viewLifecycleOwner) {
                if (it) {
                    binding.commentInput.setText("")
                }
            }
        }


    }

    companion object {
        const val TAG = "CommentFragment"
    }
}