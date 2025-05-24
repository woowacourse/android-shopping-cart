package woowacourse.shopping.presentation.ui.decorations

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    spacingDp: Float,
    edgeItemSpacingDp: Float,
    private val productViewType: Int,
) : RecyclerView.ItemDecoration() {
    private val spacingPx = dpToPx(spacingDp)
    private val edgeItemSpacingPx = dpToPx(edgeItemSpacingDp)
    private val lastColumnIndex = spanCount - 1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == INVALID_POSITION) {
            outRect.set(ZERO, ZERO, ZERO, ZERO)
            return
        }

        val adapter = parent.adapter ?: return
        val viewType = adapter.getItemViewType(position)

        if (viewType != productViewType) {
            outRect.set(ZERO, ZERO, ZERO, ZERO)
            return
        }

        var productIndex = 0
        for (i in 0 until position) {
            if (adapter.getItemViewType(i) == productViewType) {
                productIndex++
            }
        }

        val column = productIndex % spanCount
        outRect.set(
            getLeftPadding(column),
            ZERO,
            getRightPadding(column),
            ZERO,
        )
    }

    private fun getLeftPadding(column: Int): Int =
        when (column) {
            FIRST_COLUMN -> edgeItemSpacingPx
            lastColumnIndex -> spacingPx
            else -> spacingPx / DIVIDE_VALUE
        }

    private fun getRightPadding(column: Int): Int =
        when (column) {
            FIRST_COLUMN -> spacingPx
            lastColumnIndex -> edgeItemSpacingPx
            else -> spacingPx / DIVIDE_VALUE
        }

    private fun dpToPx(dp: Float): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()

    companion object {
        private const val ZERO = 0
        private const val FIRST_COLUMN = 0
        private const val INVALID_POSITION = -1
        private const val DIVIDE_VALUE = 2
    }
}
