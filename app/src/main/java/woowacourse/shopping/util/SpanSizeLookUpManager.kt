package woowacourse.shopping.util

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import woowacourse.shopping.common.ViewType

class SpanSizeLookUpManager(
    private val adapter: ConcatAdapter,
    private val spanCount: Int,
) : SpanSizeLookup() {

    override fun getSpanSize(position: Int): Int {
        return when (adapter.getItemViewType(position)) {
            ViewType.PRODUCT.ordinal -> 1
            else -> spanCount
        }
    }
}
