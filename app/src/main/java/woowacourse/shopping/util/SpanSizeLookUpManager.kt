package woowacourse.shopping.util

import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.list.ViewType
import woowacourse.shopping.list.adapter.ProductListAdapter

class SpanSizeLookUpManager(
    private val adapter: ProductListAdapter,
    private val spanCount: Int,
) : SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return if (adapter.getItemViewType(position) == ViewType.RECENT_PRODUCT.ordinal ||
            adapter.getItemViewType(position) == ViewType.LOAD_MORE.ordinal
        ) {
            spanCount
        } else 1
    }
}
