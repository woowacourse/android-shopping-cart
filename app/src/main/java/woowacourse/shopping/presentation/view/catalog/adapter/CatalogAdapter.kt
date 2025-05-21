package woowacourse.shopping.presentation.view.catalog.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.presentation.ui.layout.QuantityChangeListener
import woowacourse.shopping.presentation.view.catalog.adapter.CatalogItem.CatalogType

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
            CatalogType.PRODUCT -> ProductViewHolder.from(parent, eventListener)
            CatalogType.LOAD_MORE -> LoadMoreViewHolder.from(parent, eventListener)
        }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bind(products[position] as CatalogItem.ProductItem)
            is LoadMoreViewHolder -> Unit
        }
    }

    fun updateProducts(newItems: List<CatalogItem>) {
        val newProductItems = newItems.filterIsInstance<CatalogItem.ProductItem>()
        val hasLoadMoreItem = newItems.lastOrNull() is CatalogItem.LoadMoreItem

        updateProductItems(newProductItems)
        updateLoadMoreItem(hasLoadMoreItem)
    }

    private fun updateProductItems(newItems: List<CatalogItem.ProductItem>) {
        newItems.forEach { newItem ->
            val index = products.indexOfFirst { it is CatalogItem.ProductItem && it.product.productId == newItem.product.productId }

            if (index != -1) {
                val oldItem = products[index] as CatalogItem.ProductItem
                if (oldItem != newItem) {
                    products[index] = newItem
                    notifyItemChanged(index)
                }
            } else {
                val insertPosition = products.indexOfLast { it is CatalogItem.ProductItem } + 1
                products.add(insertPosition, newItem)
                notifyItemInserted(insertPosition)
            }
        }
    }

    private fun updateLoadMoreItem(shouldShow: Boolean) {
        val lastIndex = products.lastIndex
        val lastItem = products.lastOrNull()

        if (shouldShow) {
            if (lastItem !is CatalogItem.LoadMoreItem) {
                products.add(CatalogItem.LoadMoreItem)
                notifyItemInserted(products.lastIndex)
            }
        } else {
            if (lastItem is CatalogItem.LoadMoreItem) {
                products.removeAt(lastIndex)
                notifyItemRemoved(lastIndex)
            }
        }
    }

    interface CatalogEventListener : QuantityChangeListener {
        fun onProductClicked(productId: Long)

        fun onLoadMoreClicked()

        fun onQuantitySelectorOpenButtonClicked(productId: Long)
    }
}
