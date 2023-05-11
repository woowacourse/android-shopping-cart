package woowacourse.shopping.feature.util

import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.feature.list.adapter.ProductListAdapter
import woowacourse.shopping.feature.main.ViewType

class SpanSizeLookUpManager(
    private val adapter: ProductListAdapter,
    private val spanCount: Int,
) : SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return if (adapter.getItemViewType(position) == ViewType.HORIZONTAL.ordinal ||
            adapter.getItemViewType(position) == ViewType.ADD.ordinal
        ) {
            spanCount
        } else 1
    }
}
