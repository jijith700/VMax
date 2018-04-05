package com.jijith.vmax.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.jijith.vmax.R

/**
 * Created by jijith on 1/26/18.
 */
class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable?

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mDivider!!.intrinsicHeight

            mDivider!!.setBounds(left, top, right, bottom)
            mDivider!!.draw(c)
        }
    }

    init {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider)
//        context.resources.getDrawable(R.drawable.line_divider)
    }

}