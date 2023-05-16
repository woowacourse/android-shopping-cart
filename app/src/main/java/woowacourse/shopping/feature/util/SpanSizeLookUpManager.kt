package woowacourse.shopping.feature.util

import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.feature.list.adapter.ProductsAdapter
import woowacourse.shopping.feature.list.item.ProductView

class SpanSizeLookUpManager(
    private val adapter: ProductsAdapter,
    private val spanCount: Int,
) : SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return if (adapter.getItemViewType(position) == ProductView.TYPE_RECENT_PRODUCTS) {
            spanCount
        } else {
            1
        }
    }
}
