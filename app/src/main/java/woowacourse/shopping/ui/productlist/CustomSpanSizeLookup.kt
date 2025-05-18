package woowacourse.shopping.ui.productlist

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.R

class CustomSpanSizeLookup(
    private val adapter: RecyclerView.Adapter<*>,
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return when (adapter.getItemViewType(position)) {
            R.layout.product_item -> 1
            else -> 2
        }
    }
}
