package woowacourse.shopping.ui.shopping.recyclerview.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.ui.shopping.ShoppingViewType

class ShoppingGridLayoutManager(
    private val adapter: Adapter<ViewHolder>,
    context: Context,
    spanSize: Int = MAXIMUM_SPAN_SIZE,
) : GridLayoutManager(context, spanSize) {

    init {
        spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int =
                when (ShoppingViewType.of(adapter.getItemViewType(position))) {
                    ShoppingViewType.RECENT_PRODUCTS -> 2
                    ShoppingViewType.PRODUCT -> 1
                    ShoppingViewType.MORE_BUTTON -> 2
                }
        }
    }

    companion object {
        private const val MAXIMUM_SPAN_SIZE: Int = 2
    }
}
