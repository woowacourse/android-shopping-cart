package woowacourse.shopping.view.product.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.product.catalog.ProductCatalogEventHandler

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
            ProductCatalogItem.ViewType.PRODUCT -> ProductViewHolder.from(parent, eventHandler)
            ProductCatalogItem.ViewType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventHandler)
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = items[position]) {
            is ProductCatalogItem.ProductItem -> (holder as ProductViewHolder).bind(item.product)
            ProductCatalogItem.LoadMoreItem -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].type.ordinal

    fun updateItems(newItems: List<ProductCatalogItem>) {
        val positionStart = items.size
        val itemCount = newItems.size - positionStart

        items.clear()
        items.addAll(newItems)
        notifyItemRangeInserted(positionStart, itemCount)
    }
}
