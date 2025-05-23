package woowacourse.shopping.feature.goods.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GoodsSpanSizeLookup(
    private val adapter: RecyclerView.Adapter<*>,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int =
        when (adapter.getItemViewType(position)) {
            1 -> 2
            else -> 1
        }
}
