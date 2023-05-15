package woowacourse.shopping.shopping

import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.shopping.ShoppingRecyclerAdapter.Companion.PRODUCT_ITEM_TYPE

class ShoppingRecyclerSpanSizeManager(
    private val getViewType: (position: Int) -> Int,
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return when (getViewType(position)) {
            PRODUCT_ITEM_TYPE -> DEFAULT_SIZE
            else -> RECENT_VIEWED_LAYOUT_SIZE
        }
    }

    companion object {
        private const val DEFAULT_SIZE = 1
        private const val RECENT_VIEWED_LAYOUT_SIZE = 2
    }
}
