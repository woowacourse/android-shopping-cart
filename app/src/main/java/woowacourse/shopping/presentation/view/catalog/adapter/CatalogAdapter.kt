package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.ui.layout.QuantityChangeListener
import woowacourse.shopping.presentation.view.catalog.adapter.model.CatalogItem
import woowacourse.shopping.presentation.view.catalog.adapter.model.CatalogItem.CatalogType

class CatalogAdapter(
    products: List<CatalogItem> = emptyList(),
    private val eventListener: CatalogEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val products = products.toMutableList()

    override fun getItemCount(): Int = products.size

    override fun getItemViewType(position: Int): Int = products[position].viewType.ordinal

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (CatalogType.entries[viewType]) {
            CatalogType.RECENT_PRODUCT ->
                RecentProductContainerViewHolder.from(
                    parent,
                    eventListener,
                )

            CatalogType.PRODUCT -> ProductViewHolder.from(parent, eventListener)
            CatalogType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = products[position]
        when (holder) {
            is ProductViewHolder -> holder.bind(item as CatalogItem.ProductItem)
            is RecentProductContainerViewHolder -> holder.bind(item as CatalogItem.RecentProducts)
            is LoadMoreViewHolder -> Unit
        }
    }

    fun submitList(newItems: List<CatalogItem>) {
        val recentProduct = newItems.filterIsInstance<CatalogItem.RecentProducts>().firstOrNull()
        val productItems = newItems.filterIsInstance<CatalogItem.ProductItem>()
        val shouldShowLoadMore = newItems.lastOrNull() is CatalogItem.LoadMoreItem

        updateRecentProductItem(recentProduct)
        updateList(productItems)
        updateLoadMoreItem(shouldShowLoadMore)
    }

    private fun updateRecentProductItem(newItem: CatalogItem.RecentProducts?) {
        if (newItem == null) return

        val index = products.indexOfFirst { it is CatalogItem.RecentProducts }
        if (index == INDEX_NOT_FOUND) {
            products.add(FIRST_POSITION, newItem)
            notifyItemInserted(FIRST_POSITION)
            return
        }

        val currentItem = products[index] as CatalogItem.RecentProducts
        if (currentItem.products != newItem.products) {
            products[index] = newItem
            notifyItemChanged(index)
        }
    }

    private fun filteredProductItems(): List<CatalogItem.ProductItem> = products.filterIsInstance<CatalogItem.ProductItem>()

    private fun updateList(newList: List<CatalogItem.ProductItem>) {
        val oldItemsMap = filteredProductItems().associateBy { it.productId }
        val newItemsMap = newList.associateBy { it.productId }

        oldItemsMap.forEach { (oldItemProductId, oldItem) ->
            val newItem = newItemsMap[oldItemProductId]
            val index =
                products.indexOfFirst { (it as? CatalogItem.ProductItem)?.productId == oldItemProductId }

            if (newItem == null && index != INDEX_NOT_FOUND) {
                products.removeAt(index)
                notifyItemRemoved(index)
                return@forEach
            }

            if (newItem != oldItem && index != INDEX_NOT_FOUND) {
                products[index] = (newItem as CatalogItem.ProductItem)
                notifyItemChanged(index)
            }
        }

        newList.forEach { newItem ->
            if (!oldItemsMap.containsKey(newItem.productId)) {
                val insertIndex = products.size
                products.add(insertIndex, newItem)
                notifyItemInserted(insertIndex)
            }
        }
    }

    private fun updateLoadMoreItem(shouldShow: Boolean) {
        val loadMoreIndex = products.indexOfFirst { it is CatalogItem.LoadMoreItem }
        if (loadMoreIndex != INDEX_NOT_FOUND && loadMoreIndex != products.lastIndex) {
            products.removeAt(loadMoreIndex)
            notifyItemRemoved(loadMoreIndex)
        }

        val hasLoadMoreItem = products.lastOrNull() is CatalogItem.LoadMoreItem

        if (shouldShow && !hasLoadMoreItem) {
            products.add(CatalogItem.LoadMoreItem)
            notifyItemInserted(products.lastIndex)
            return
        }

        if (!shouldShow && hasLoadMoreItem) {
            val lastIndex = products.lastIndex
            products.removeAt(lastIndex)
            notifyItemRemoved(lastIndex)
        }
    }

    interface CatalogEventListener : QuantityChangeListener {
        fun onProductClicked(productId: Long)

        fun onLoadMoreClicked()

        fun onQuantitySelectorOpenButtonClicked(productId: Long)
    }

    companion object {
        private const val INDEX_NOT_FOUND = -1
        private const val FIRST_POSITION = 0
    }
}
