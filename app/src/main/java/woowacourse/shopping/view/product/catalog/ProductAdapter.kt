package woowacourse.shopping.view.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product

class ProductAdapter(
    private val eventHandler: ProductCatalogEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Product>()
    private var hasNext: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            PRODUCT -> ProductViewHolder.from(parent, eventHandler)
            LOAD_MORE -> LoadMoreViewHolder.from(parent, eventHandler)
            else -> throw IllegalArgumentException()
        }

    override fun getItemCount(): Int = items.size + if (hasNext) LOAD_MORE_BUTTON_COUNT else 0

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (getItemViewType(position)) {
            PRODUCT -> (holder as ProductViewHolder).bind(items[position])
            LOAD_MORE -> {}
        }
    }

    override fun getItemViewType(position: Int): Int = if (hasNext && position == items.size) LOAD_MORE else PRODUCT

    fun addAll(
        newItems: List<Product>,
        hasNext: Boolean,
    ) {
        val startIndex = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startIndex, newItems.size)

        this.hasNext = hasNext
        if (!hasNext) {
            notifyItemRemoved(items.size)
        } else if (startIndex == 0) {
            notifyItemInserted(items.size)
        }
    }

    companion object {
        const val PRODUCT = 0
        const val LOAD_MORE = 1
        private const val LOAD_MORE_BUTTON_COUNT = 1
    }
}
