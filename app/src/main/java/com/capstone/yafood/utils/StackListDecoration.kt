package com.capstone.yafood.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StackListDecoration(
    private val horizontalSpacing: Int = 0,
    private val verticalSpacing: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildLayoutPosition(view) > 0) {
            outRect.left = horizontalSpacing
            outRect.top = verticalSpacing
        }
    }
}