package woowacourse.shopping.ui.catalog.adapter.product

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatalogLayoutManager(
    context: Context,
    private val productsAdapter: RecyclerView.Adapter<CatalogItemViewHolder<CatalogItem, ViewDataBinding>>,
) : GridLayoutManager(context, GRID_LAYOUT_SIZE) {
    init {
        spanSizeLookup =
            object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = productsAdapter.getItemViewType(position)
                    return when (CatalogItemViewType.entries[viewType]) {
                        CatalogItemViewType.PRODUCT -> PRODUCT_LAYOUT_SIZE
                        CatalogItemViewType.LOAD_MORE -> LOAD_MORE_LAYOUT_SIZE
                    }
                }
            }
    }

    companion object {
        private const val GRID_LAYOUT_SIZE: Int = 2
        private const val PRODUCT_LAYOUT_SIZE: Int = 1
        private const val LOAD_MORE_LAYOUT_SIZE: Int = 2
    }
}
