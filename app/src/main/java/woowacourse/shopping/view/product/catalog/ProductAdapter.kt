package woowacourse.shopping.view.product.catalog

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.product.OnProductListener

class ProductAdapter(
    private val productsEventListener: OnProductListener,
    private val loadEventListener: OnLoadEventListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<ProductItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        when (viewType) {
            PRODUCT -> ProductViewHolder.from(parent, productsEventListener)
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
                (holder as ProductViewHolder).bind(productItem.product)
            }
            LOAD_MORE -> {}
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (items[position]) {
            is ProductItem.CatalogProduct -> PRODUCT
            is ProductItem.LoadMore -> LOAD_MORE
        }

    fun addItems(newItems: List<ProductItem>) {
        removeLoadMoreIfExists()

        val startIndex = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(startIndex, newItems.size)
    }

    private fun removeLoadMoreIfExists() {
        if (items.lastOrNull() is ProductItem.LoadMore) {
            val removeIndex = items.lastIndex
            items.removeAt(removeIndex)
            notifyItemRemoved(removeIndex)
        }
    }

    companion object {
        const val PRODUCT = 0
        const val LOAD_MORE = 1
    }
}
