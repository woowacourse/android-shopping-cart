package woowacourse.shopping.presentation.productlist

import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.presentation.productlist.product.ProductListViewType

class ProductListSpanSizeLookup(private val getViewType: (Int) -> Int) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return when (getViewType(position)) {
            ProductListViewType.RECENT_PRODUCT.number -> RECENT_PRODUCT_SPAN_SIZE
            ProductListViewType.MORE_ITEM.number -> MORE_ITEM_SPAN_SIZE
            else -> DEFAULT_SPAN_SIZE
        }
    }

    companion object {
        private const val RECENT_PRODUCT_SPAN_SIZE = 2
        private const val MORE_ITEM_SPAN_SIZE = 2
        private const val DEFAULT_SPAN_SIZE = 1
    }
}
