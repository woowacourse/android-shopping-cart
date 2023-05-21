package woowacourse.shopping.ui.shopping.recyclerview.layoutmanager

import android.content.Context
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.shopping.ui.shopping.ShoppingViewType

class ShoppingGridLayoutManager(
    context: Context,
    adapter: Adapter<ViewHolder>,
    maxSpanSize: Int = DEFAULT_MAXIMUM_SPAN_SIZE,
) : GridLayoutManager(context, maxSpanSize) {

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
        private const val DEFAULT_MAXIMUM_SPAN_SIZE: Int = 2

        @JvmStatic
        @JvmOverloads
        fun create(
            context: Context,
            adapter: ConcatAdapter,
            spanSize: Int = DEFAULT_MAXIMUM_SPAN_SIZE,
        ): ShoppingGridLayoutManager = ShoppingGridLayoutManager(context, adapter, spanSize)
    }
}
