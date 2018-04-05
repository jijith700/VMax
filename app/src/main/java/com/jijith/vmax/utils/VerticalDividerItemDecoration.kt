package com.jijith.vmax.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by jijith on 1/26/18.
 */
class VerticalDividerItemDecoration(value: Int, private val verticalOrientation: Boolean) : RecyclerView.ItemDecoration() {

    private val space: Int = value

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State?) {
        // skip first item in the list
        if (parent.getChildAdapterPosition(view) != 0) {

            if (verticalOrientation) {

                outRect.set(space, 0, 0, 0)

            } else if (!verticalOrientation) {

                outRect.set(0, space, 0, 0)
            }
        }
    }
}