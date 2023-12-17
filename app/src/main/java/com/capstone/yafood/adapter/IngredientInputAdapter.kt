package com.capstone.yafood.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.yafood.databinding.ItemIngredientInputBinding

class IngredientInputAdapter(
    private val listItem: List<String>,
    private val handleDelete: (position: Int) -> Unit,
    private val handleChange: (text: String, position: Int) -> Unit,
    private val hint: String? = null,
    private val minPostFocus: Int = 2
) : RecyclerView.Adapter<IngredientInputAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemIngredientInputBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemIngredientInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = listItem[position]

        holder.binding.apply {
            ingredientInput.setText(item)
            hint?.let {
                ingredientInput.hint = it
            }
            if (listItem.size > minPostFocus && position == listItem.size - 1) {
                ingredientInput.requestFocus()
            }
            ingredientInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    handleChange(s.toString(), position)
                }

            })
            btnDelete.setOnClickListener {
                handleDelete(position)
            }
        }
    }
}