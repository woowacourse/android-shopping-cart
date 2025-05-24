package woowacourse.shopping.view.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import woowacourse.shopping.view.core.base.BaseViewHolder
import woowacourse.shopping.view.core.handler.CartQuantityHandler
import woowacourse.shopping.view.main.adapter.recent.RecentProductViewHolder
import woowacourse.shopping.view.main.state.HistoryState
import woowacourse.shopping.view.main.state.LoadState
import woowacourse.shopping.view.main.state.ProductState
import woowacourse.shopping.view.main.state.ProductUiState

class ProductAdapter(
    items: List<ProductRvItems>,
    private val handler: ProductAdapterEventHandler,
) : RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {
    private val items: MutableList<ProductRvItems> = items.toMutableList()
    private var historyItemsCache: List<HistoryState> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<ViewBinding> {
        return when (ProductRvItems.ViewType.entries[viewType]) {
            ProductRvItems.ViewType.VIEW_TYPE_PRODUCT ->
                ProductViewHolder(
                    parent,
                    handler as ProductViewHolder.Handler,
                    handler as CartQuantityHandler,
                )

            ProductRvItems.ViewType.VIEW_TYPE_LOAD ->
                LoadViewHolder(parent, handler)

            ProductRvItems.ViewType.VIEW_TYPE_RECENT_PRODUCT ->
                RecentProductViewHolder(parent, handler)

            ProductRvItems.ViewType.VIEW_TYPE_DIVIDER ->
                DividerViewHolder(parent)
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ViewBinding>,
        position: Int,
    ) {
        when (val item = items[position]) {
            is ProductRvItems.ProductItem -> (holder as ProductViewHolder).bind(item)
            is ProductRvItems.LoadItem -> (holder as LoadViewHolder).bind(item)
            is ProductRvItems.RecentProductItem -> (holder as RecentProductViewHolder).bind(item)
            ProductRvItems.DividerItem -> Unit
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    fun submitList(newUiState: ProductUiState) {
        removeLoadItem()
        updateHistorySection(newUiState.historyItems)
        updateProductItems(newUiState.productItems)
        generateLoad(newUiState.load)
    }

    private fun updateHistorySection(newHistories: List<HistoryState>) {
        if (newHistories == historyItemsCache) return

        val recentIndex = items.indexOfFirst { it is ProductRvItems.RecentProductItem }
        if (recentIndex != -1) {
            items[recentIndex] = ProductRvItems.RecentProductItem(newHistories)
            notifyItemChanged(recentIndex)
        } else {
            items.add(HISTORY_INSERT_POSITION, ProductRvItems.RecentProductItem(newHistories))
            items.add(DIVIDER_INSERT_POSITION, ProductRvItems.DividerItem)
            notifyItemRangeInserted(HISTORY_INSERT_POSITION, WHEN_HAS_HISTORY_PRODUCT_POSITION)
        }

        historyItemsCache = newHistories
    }

    private fun updateProductItems(newProducts: List<ProductState>) {
        newProducts.forEachIndexed { index, newState ->
            val offset = getProductItemOffset()
            val actualIndex = offset + index
            val existingItem = items.getOrNull(actualIndex) as? ProductRvItems.ProductItem
            val newItem = ProductRvItems.ProductItem(newState)

            applyItemChange(existingItem, newItem, actualIndex)
        }
    }

    private fun applyItemChange(
        existingItem: ProductRvItems.ProductItem?,
        newItem: ProductRvItems.ProductItem,
        index: Int,
    ) {
        when {
            existingItem == null -> {
                items.add(index, newItem)
                notifyItemInserted(index)
            }

            !areContentsTheSame(existingItem, newItem) -> {
                items[index] = newItem
                notifyItemChanged(index)
            }

            else -> return
        }
    }

    private fun areContentsTheSame(
        oldItem: ProductRvItems.ProductItem,
        newItem: ProductRvItems.ProductItem,
    ): Boolean {
        return oldItem.data.item == newItem.data.item &&
            oldItem.data.cartQuantity == newItem.data.cartQuantity
    }

    private fun generateLoad(load: LoadState) {
        if (load is LoadState.CanLoad) {
            items.add(ProductRvItems.LoadItem)
            notifyItemInserted(items.size - 1)
        }
    }

    private fun removeLoadItem() {
        val index = items.indexOfFirst { it is ProductRvItems.LoadItem }
        if (index != -1) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    private fun getProductItemOffset(): Int {
        val hasHistory = items.any { it is ProductRvItems.RecentProductItem }
        return if (hasHistory) WHEN_HAS_HISTORY_PRODUCT_POSITION else DEFAULT_PRODUCT_POSITION
    }

    interface Handler :
        LoadViewHolder.Handler,
        ProductViewHolder.Handler,
        HistoryViewHolder.Handler

    companion object {
        private const val HISTORY_INSERT_POSITION = 0
        private const val DIVIDER_INSERT_POSITION = 1
        private const val DEFAULT_PRODUCT_POSITION = 0
        private const val WHEN_HAS_HISTORY_PRODUCT_POSITION = 2
    }
}
