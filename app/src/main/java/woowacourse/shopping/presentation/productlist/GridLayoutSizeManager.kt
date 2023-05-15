package woowacourse.shopping.presentation.productlist

import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.presentation.model.ProductViewType

class GridLayoutSizeManager(private val getViewType: (Int) -> Int) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return when (getViewType(position)) {
            ProductViewType.RECENT_PRODUCTS_VIEW_TYPE_NUMBER -> RECENT_PRODUCT_SPAN_SIZE
            ProductViewType.MORE_ITEM_VIEW_TYPE_NUMBER -> MORE_ITEM_SPAN_SIZE
            else -> DEFAULT_SPAN_SIZE
        }
    }

    companion object {
        private const val RECENT_PRODUCT_SPAN_SIZE = 2
        private const val MORE_ITEM_SPAN_SIZE = 2
        private const val DEFAULT_SPAN_SIZE = 1
    }
}
