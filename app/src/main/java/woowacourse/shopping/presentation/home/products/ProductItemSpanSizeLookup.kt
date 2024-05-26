package woowacourse.shopping.presentation.home.products

import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.presentation.home.products.ProductAdapter

class ProductItemSpanSizeLookup(private val productAdapter: ProductAdapter) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        val isLastItem = position == productAdapter.itemCount - 1
        return if (isLastItem) 2 else 1
    }
}
