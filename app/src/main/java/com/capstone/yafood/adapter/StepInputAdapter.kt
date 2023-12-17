package com.capstone.yafood.adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.yafood.databinding.ItemStepInputBinding

class StepInputAdapter(
    private val listStepValue: List<String>,
    private val handleDelete: (position: Int) -> Unit,
    private val handleChange: (text: String, position: Int) -> Unit,

    ) : RecyclerView.Adapter<StepInputAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemStepInputBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemStepInputBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = listStepValue.size

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val stepValue = listStepValue[position]

        holder.binding.apply {
            number.text = "${position + 1}"
            stepInput.setText(stepValue)
            if (listStepValue.size > 2 && position == listStepValue.size - 1) {
                stepInput.requestFocus()
            }
            stepInput.addTextChangedListener(object : TextWatcher {
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