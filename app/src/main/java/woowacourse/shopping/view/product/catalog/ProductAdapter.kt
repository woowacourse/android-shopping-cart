package woowacourse.shopping.view.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.product.OnQuantityControlListener

class ProductAdapter(
    private val categoryEventListener: OnCategoryEventListener,
    private val onQuantityControlListener: OnQuantityControlListener,
    private val loadEventListener: OnLoadEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<ProductItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            PRODUCT -> ProductViewHolder.from(parent, onQuantityControlListener, categoryEventListener)
            LOAD_MORE -> LoadMoreViewHolder.from(parent, loadEventListener)
            else -> throw IllegalArgumentException()
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (getItemViewType(position)) {
            PRODUCT -> {
                val productItem = items[position] as ProductItem.CatalogProduct
                (holder as ProductViewHolder).bind(productItem)
            }
            LOAD_MORE -> {}
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is ProductItem.CatalogProduct -> PRODUCT
            is ProductItem.LoadMore -> LOAD_MORE
        }

    private fun appendItems(newItems: List<ProductItem>) {
        removeLoadMoreIfExists()
        val startIndex = items.size
        val itemsToAdd = newItems.drop(startIndex)
        items.addAll(itemsToAdd)
        notifyItemRangeInserted(startIndex, itemsToAdd.size)
    }

    private fun updateItems(newItems: List<ProductItem>) {
        val oldItems = items.toList()

        val commonSize = minOf(oldItems.size, newItems.size)
        for (i in 0 until commonSize) {
            if (oldItems[i] != newItems[i]) {
                items[i] = newItems[i]
                notifyItemChanged(i)
            }
        }
    }

    private fun removeLoadMoreIfExists() {
        if (items.lastOrNull() is ProductItem.LoadMore) {
            val removeIndex = items.lastIndex
            items.removeAt(removeIndex)
            notifyItemRemoved(removeIndex)
        }
    }

    fun updateProductItems(newItems: List<ProductItem>) {
        if (this.items.size == newItems.size) {
            updateItems(newItems)
        } else {
            appendItems(newItems)
        }
    }

    companion object {
        const val PRODUCT = 0
        const val LOAD_MORE = 1
    }
}
