package com.rehat.rehatcoffee.presentation.common.extention

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class DividerDecoration(private val dividerDrawable: Drawable?) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + (dividerDrawable?.intrinsicHeight ?: 0)

            dividerDrawable?.setBounds(left, top, right, bottom)
            dividerDrawable?.draw(canvas)
        }
    }
}