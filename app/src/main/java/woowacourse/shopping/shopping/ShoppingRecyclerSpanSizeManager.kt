package woowacourse.shopping.shopping

import androidx.recyclerview.widget.GridLayoutManager

class ShoppingRecyclerSpanSizeManager(
    private val getViewType: (position: Int) -> Int
) : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {

        return when (getViewType(position)) {
            ShoppingRecyclerItemViewType.RECENT_VIEWED.ordinal -> WIDE
            ShoppingRecyclerItemViewType.READ_MORE.ordinal -> WIDE
            else -> DEFAULT
        }
    }

    companion object {
        private const val DEFAULT = 1
        private const val WIDE = 2
    }
}
