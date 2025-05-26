package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.model.CatalogItem
import woowacourse.shopping.presentation.model.CatalogItem.CatalogType
import woowacourse.shopping.presentation.ui.layout.QuantityChangeListener

class CatalogAdapter(
    items: List<CatalogItem> = emptyList(),
    private val eventListener: CatalogEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val recentProductsAdapter = RecentProductAdapter(emptyList(), eventListener)
    private val catalogItems = items.toMutableList()

    override fun getItemCount(): Int = catalogItems.size

    override fun getItemViewType(position: Int): Int = catalogItems[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (CatalogType.entries[viewType]) {
            CatalogType.RECENT_PRODUCT ->
                RecentProductContainerViewHolder.from(
                    parent,
                    recentProductsAdapter,
                )

            CatalogType.PRODUCT -> ProductViewHolder.from(parent, eventListener)
            CatalogType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = catalogItems[position]
        when (holder) {
            is ProductViewHolder -> holder.bind(item as CatalogItem.ProductItem)
            is RecentProductContainerViewHolder -> holder.bind(item as CatalogItem.RecentProducts)
            is LoadMoreViewHolder -> Unit
        }
    }

    fun submitList(newItems: List<CatalogItem>) {
        newItems.forEachIndexed { index, newItem ->
            val oldItem = catalogItems.getOrNull(index)

            if (oldItem == null) {
                catalogItems.add(index, newItem)
                notifyItemInserted(index)
                return@forEachIndexed
            }

            if (oldItem != newItem) {
                catalogItems[index] = newItem
                notifyItemChanged(index)
            }
        }
    }

    interface CatalogEventListener : QuantityChangeListener {
        fun onProductClick(productId: Long)

        fun onLoadMoreClick()

        fun onQuantitySelectorOpenButtonClick(productId: Long)
    }
}
