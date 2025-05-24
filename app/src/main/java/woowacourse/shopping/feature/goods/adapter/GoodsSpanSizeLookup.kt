package woowacourse.shopping.feature.goods.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GoodsSpanSizeLookup(
    private val adapter: RecyclerView.Adapter<*>,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int =
        when (ItemViewType.from(adapter.getItemViewType(position))) {
            ItemViewType.HISTORY -> 2
            ItemViewType.GOODS -> 1
            ItemViewType.LOAD_MORE -> 2
        }
}
