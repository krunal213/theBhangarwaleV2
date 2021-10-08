package com.app.thebhangarwale

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(mContext: Context) : RecyclerView.ItemDecoration() {
    private var mDivider: Drawable? = null
    init {
        mDivider = ContextCompat.getDrawable(mContext, R.drawable.drawable_horizonatal_divider)
    }
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childMainView = parent.getChildAt(0)
        val dividerLeft = childMainView.left
        val dividerRight = childMainView.right
        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + mDivider!!.intrinsicHeight
            mDivider!!.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            mDivider!!.draw(c)
        }
    }

}
