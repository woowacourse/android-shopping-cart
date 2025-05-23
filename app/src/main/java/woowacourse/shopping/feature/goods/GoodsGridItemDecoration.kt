package woowacourse.shopping.feature.goods

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.feature.goods.adapter.vertical.GoodsAdapter

class GoodsGridItemDecoration(
    private val concatAdapter: ConcatAdapter,
    private val horizontalPaddingDp: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val pair = concatAdapter.getWrappedAdapterAndPosition(position)
        val adapter = pair.first
        val localPosition = pair.second

        if (adapter is GoodsAdapter) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanCount = layoutManager.spanCount
            val column = localPosition % spanCount

            val horizontalPaddingPx =
                (horizontalPaddingDp * view.context.resources.displayMetrics.density + 0.5f).toInt()

            when (column) {
                0 -> outRect.left = horizontalPaddingPx
                1 -> outRect.right = horizontalPaddingPx
            }
        }
    }
}
