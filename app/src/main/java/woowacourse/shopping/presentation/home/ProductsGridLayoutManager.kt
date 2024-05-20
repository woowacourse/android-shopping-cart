package woowacourse.shopping.presentation.home

import androidx.recyclerview.widget.GridLayoutManager
import woowacourse.shopping.presentation.home.adapter.ProductAdapter

class ProductsGridLayoutManager(
    private val adapter: ProductAdapter,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return when (adapter.getItemViewType(position)) {
            ProductAdapter.TYPE_PRODUCT -> 1
            ProductAdapter.TYPE_LOAD -> 2
            else -> throw IllegalArgumentException(EXCEPTION_ILLEGAL_VIEW_TYPE)
        }
    }

    companion object {
        private const val EXCEPTION_ILLEGAL_VIEW_TYPE = "유효하지 않은 뷰 타입입니다."
    }
}
