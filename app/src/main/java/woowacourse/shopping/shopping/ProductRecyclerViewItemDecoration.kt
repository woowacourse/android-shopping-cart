package woowacourse.shopping.shopping

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductRecyclerViewItemDecoration(
    private val layoutManager: GridLayoutManager,
    private val spacing: Int,
    private val spanCount: Int,
) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val position = parent.getChildAdapterPosition(view)
        val spanSize = layoutManager.spanSizeLookup.getSpanSize(position)

        if (spanSize != spanCount) {
            outRect.left = spacing
            outRect.right = spacing
        }

        outRect.top = spacing
    }
}
