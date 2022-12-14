package com.example.diagnaltest.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ContentLayoutItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            this.left = 16
            this.right = 16
            this.bottom = 90
        }
    }
}
