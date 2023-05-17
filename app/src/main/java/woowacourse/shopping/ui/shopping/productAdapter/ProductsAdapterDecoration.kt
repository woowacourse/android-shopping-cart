package woowacourse.shopping.ui.shopping.productAdapter

import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R

object ProductsAdapterDecoration {

    fun getItemDecoration(layoutManager: GridLayoutManager, resources: Resources) = object : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val spacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
            val position = parent.getChildAdapterPosition(view)
            val spanSize = layoutManager.spanSizeLookup.getSpanSize(position)

            if (spanSize != layoutManager.spanCount) {
                outRect.left = spacing
                outRect.right = spacing
                outRect.top = spacing
            }
        }
    }

    fun getSpanSizeLookup(layoutManager: GridLayoutManager, adapter: ProductsAdapter) = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (adapter.getItemViewType(position)) {
                ProductsItemType.TYPE_FOOTER -> layoutManager.spanCount
                ProductsItemType.TYPE_ITEM -> 1
                else -> layoutManager.spanCount
            }
        }
    }
}
