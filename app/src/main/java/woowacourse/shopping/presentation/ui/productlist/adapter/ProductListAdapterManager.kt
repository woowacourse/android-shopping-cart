package woowacourse.shopping.presentation.ui.productlist.adapter

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductListAdapterManager(
    context: Context,
    private val adapter: RecyclerView.Adapter<*>,
    spanCount: Int,
    private val productViewType: Int,
) : GridLayoutManager(context, spanCount) {
    init {
        spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (adapter.getItemViewType(position) == productViewType) {
                        1
                    } else {
                        spanCount
                    }
                }
            }
    }
}
