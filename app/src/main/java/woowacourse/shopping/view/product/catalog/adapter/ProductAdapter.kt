package woowacourse.shopping.view.product.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.Product

class ProductAdapter(
    items: List<Product> = emptyList(),
    private val eventHandler: ProductCatalogEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = items.toMutableList()
    private var hasNext: Boolean = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (Type.entries[viewType]) {
            Type.PRODUCT -> ProductViewHolder.from(parent, eventHandler)
            Type.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventHandler)
        }

    override fun getItemCount(): Int = items.size + if (hasNext) LOAD_MORE_BUTTON_COUNT else 0

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (Type.entries[getItemViewType(position)]) {
            Type.PRODUCT -> (holder as ProductViewHolder).bind(items[position])
            Type.LOAD_MORE -> {}
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (hasNext && position == items.size) Type.LOAD_MORE.ordinal else Type.PRODUCT.ordinal

    fun updateItems(
        newItems: List<Product>,
        hasNext: Boolean,
    ) {
        val positionStart = items.size
        val itemCount = newItems.size - positionStart

        items.clear()
        items.addAll(newItems)
        notifyItemRangeInserted(positionStart, itemCount)

        this.hasNext = hasNext
        if (!hasNext) {
            notifyItemRemoved(items.size)
        }
    }

    enum class Type {
        PRODUCT,
        LOAD_MORE,
    }

    companion object {
        private const val LOAD_MORE_BUTTON_COUNT = 1
    }
}
