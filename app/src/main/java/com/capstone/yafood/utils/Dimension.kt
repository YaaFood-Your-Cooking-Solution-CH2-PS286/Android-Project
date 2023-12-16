package com.capstone.yafood.utils

import android.content.res.Resources
import android.util.TypedValue
import android.view.View

fun dpValue(dp: Float, view: View): Int {
    val metrics = view.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
}