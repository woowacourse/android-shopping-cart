package woowacourse.shopping.presentation.ui.decorations

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalEdgeSpacingDecoration(
    edgeSpacingDp: Float = 20f,
    innerSpacingDp: Float = 4f,
) : RecyclerView.ItemDecoration() {
    private val edgeSpacingPx = dpToPx(edgeSpacingDp)
    private val innerSpacingPx = dpToPx(innerSpacingDp)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: return

        when (position) {
            0 -> outRect.set(edgeSpacingPx, 0, innerSpacingPx, 0)
            itemCount - 1 -> outRect.set(innerSpacingPx, 0, edgeSpacingPx, 0)
            else -> outRect.set(innerSpacingPx, 0, innerSpacingPx, 0)
        }
    }

    private fun dpToPx(dp: Float): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
}
