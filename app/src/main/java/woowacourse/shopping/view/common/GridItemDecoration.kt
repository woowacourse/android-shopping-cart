package woowacourse.shopping.view.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(
    context: Context,
    private val viewTypeToDecorate: Int,
    private val spanCount: Int,
    sideMarginDp: Int,
    itemSpacingDp: Int,
) : RecyclerView.ItemDecoration() {
    private val sideMarginPx = dpToPx(context, sideMarginDp)
    private val itemSpacingPx = dpToPx(context, itemSpacingDp)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val adapter = parent.adapter ?: return
        val viewType = adapter.getItemViewType(position)

        if (viewType != viewTypeToDecorate) {
            outRect.set(0, 0, 0, 0)
            return
        }

        var gridIndex = -1
        for (i in 0..position) {
            if (adapter.getItemViewType(i) == viewTypeToDecorate) {
                gridIndex++
            }
        }

        val column = gridIndex % spanCount

        if (column == 0) {
            outRect.left = sideMarginPx
            outRect.right = itemSpacingPx / 2
        } else {
            outRect.left = itemSpacingPx / 2
            outRect.right = sideMarginPx
        }

        outRect.top = 0
        outRect.bottom = itemSpacingPx
    }

    private fun dpToPx(
        context: Context,
        dp: Int,
    ): Int = (dp * context.resources.displayMetrics.density).toInt()
}
