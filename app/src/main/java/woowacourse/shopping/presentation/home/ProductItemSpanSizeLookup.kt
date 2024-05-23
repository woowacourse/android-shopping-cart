package woowacourse.shopping.presentation.home

import androidx.recyclerview.widget.GridLayoutManager

class ProductItemSpanSizeLookup(private val productAdapter: ProductAdapter) :
    GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        val isLastItem = position == productAdapter.itemCount - 1
        return if (isLastItem || position == 0) 2 else 1
    }
}
