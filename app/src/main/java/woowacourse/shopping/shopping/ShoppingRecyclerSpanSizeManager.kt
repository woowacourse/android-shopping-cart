package woowacourse.shopping.shopping

import androidx.recyclerview.widget.GridLayoutManager

class ShoppingRecyclerSpanSizeManager : GridLayoutManager.SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return when (position) {
            INITIAL_POSITION -> RECENT_VIEWED_LAYOUT_SIZE
            else -> DEFAULT_SIZE
        }
    }

    companion object {
        private const val INITIAL_POSITION = 0
        private const val DEFAULT_SIZE = 1
        private const val RECENT_VIEWED_LAYOUT_SIZE = 2
    }
}
