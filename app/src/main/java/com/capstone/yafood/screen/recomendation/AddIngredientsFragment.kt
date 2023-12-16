package com.capstone.yafood.screen.recomendation

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.yafood.R
import com.capstone.yafood.adapter.IngredientInputAdapter
import com.capstone.yafood.databinding.FragmentAddIngredientsBinding
import com.capstone.yafood.screen.ViewModelFactory
import com.capstone.yafood.utils.ListSpaceDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddIngredientsFragment(
    private val setListValue: (items: List<String>) -> Unit
) : BottomSheetDialogFragment() {

    private var binding: FragmentAddIngredientsBinding? = null
    private val viewModel by activityViewModels<AddIngredientsViewModel> {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIngredientsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupComponents()
    }

    private fun setupComponents() {
        binding?.let {
            viewModelObserver(it)
            it.btnAddIngredientInput.setOnClickListener {
                viewModel.addInput()
            }
            it.rvIngredientsInput.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                addItemDecoration(ListSpaceDecoration(verticalSpacing = 16))
            }
            it.btnSubmit.setOnClickListener {
                setListValue(viewModel.listValue)
                viewModel.clearList()
                dismiss()
            }
        }
    }

    private fun viewModelObserver(bind: FragmentAddIngredientsBinding) {
        viewModel.listInput.observe(viewLifecycleOwner) {
            bind.rvIngredientsInput.adapter =
                IngredientInputAdapter(
                    it,
                    viewModel::deleteInput,
                    viewModel::updateValue,
                    requireActivity().getString(R.string.dummy_ingredient)
                )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val TAG = "AddIngredientFragment"
    }
}