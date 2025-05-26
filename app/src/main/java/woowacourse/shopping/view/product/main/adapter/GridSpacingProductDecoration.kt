package woowacourse.shopping.view.product.main.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingProductDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val edgeSpacing: Int,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        val viewType = parent.adapter?.getItemViewType(position)

        if (viewType == ViewItems.ViewType.PRODUCTS.ordinal
        ) {
            outRect.top = edgeSpacing

            if (column == 0) {
                outRect.left = edgeSpacing
                outRect.right = spacing / 2
            } else {
                outRect.left = spacing / 2
                outRect.right = edgeSpacing
            }
        } else if (viewType == ViewItems.ViewType.SHOW_MORE.ordinal ||
            viewType == ViewItems.ViewType.RECENTLY_VIEWED_PRODUCTS.ordinal
        ) {
            outRect.left = edgeSpacing
            outRect.right = edgeSpacing
        }
    }
}
