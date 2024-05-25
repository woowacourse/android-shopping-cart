package woowacourse.shopping.ui.products.adapter

import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.ui.products.adapter.type.ProductsViewType

class ProductsSpanSizeLookUp(private val adapter: ProductsAdapter) : SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        val productsViewType = ProductsViewType.from(adapter.getItemViewType(position))
        return productsViewType.span
    }
}
