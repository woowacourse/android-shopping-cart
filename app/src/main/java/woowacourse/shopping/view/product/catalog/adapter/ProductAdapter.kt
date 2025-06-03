package woowacourse.shopping.view.product.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.product.catalog.ProductCatalogEventHandler
import woowacourse.shopping.view.product.catalog.adapter.recent.RecentProductListViewHolder

class ProductAdapter(
    items: List<ProductCatalogItem> = emptyList(),
    private val eventHandler: ProductCatalogEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = items.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (ProductCatalogItem.ViewType.entries[viewType]) {
            ProductCatalogItem.ViewType.RECENT_PRODUCT -> RecentProductListViewHolder.from(parent, eventHandler)
            ProductCatalogItem.ViewType.PRODUCT -> ProductViewHolder.from(parent, eventHandler)
            ProductCatalogItem.ViewType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventHandler)
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = items[position]) {
            is ProductCatalogItem.RecentProductsItem ->
                (holder as RecentProductListViewHolder).bind(item)

            is ProductCatalogItem.ProductItem -> (holder as ProductViewHolder).bind(item)
            ProductCatalogItem.LoadMoreItem -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].type.ordinal

    fun updateItems(newItems: List<ProductCatalogItem>) {
        val oldSize = items.size
        val newSize = newItems.size
        val minSize = minOf(oldSize, newSize)

        for (i in 0 until minSize) {
            val oldItem = items[i]
            val newItem = newItems[i]

            if (oldItem != newItem) {
                items[i] = newItem
                notifyItemChanged(i)
            }
        }

        if (newSize > oldSize) {
            val addedItems = newItems.subList(oldSize, newSize)
            items.addAll(addedItems)
            notifyItemRangeInserted(oldSize, addedItems.size)
        }
    }
}
