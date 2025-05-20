package woowacourse.shopping.ui.products.adapter.product

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.CartProduct

class ProductsAdapter(
    private val onClickHandler: OnClickHandler,
) : RecyclerView.Adapter<ProductsItemViewHolder<ProductsItem, ViewDataBinding>>() {
    private val items: MutableList<ProductsItem> = mutableListOf()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsItemViewHolder<ProductsItem, ViewDataBinding> =
        when (ProductsItemViewType.entries[viewType]) {
            ProductsItemViewType.PRODUCT -> ProductViewHolder(parent, onClickHandler)
            ProductsItemViewType.LOAD_MORE -> LoadMoreViewHolder(parent, onClickHandler)
        } as ProductsItemViewHolder<ProductsItem, ViewDataBinding>

    override fun onBindViewHolder(
        holder: ProductsItemViewHolder<ProductsItem, ViewDataBinding>,
        position: Int,
    ) {
        val productsItem: ProductsItem = items[position]
        holder.bind(productsItem)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    fun submitItems(
        newItems: List<CartProduct>,
        hasMore: Boolean,
    ) {
        val newProductItems = newItems.map { ProductsItem.ProductItem(it) }
        val oldProductItems = items.toList()

        updateNewItems(newProductItems, oldProductItems)
        removeExceedingItems(oldProductItems, newProductItems)
        updateLoadMoreItem(hasMore)
    }

    private fun updateNewItems(
        newProductItems: List<ProductsItem.ProductItem>,
        oldProductItems: List<ProductsItem>,
    ) {
        for ((position, newItem) in newProductItems.withIndex()) {
            val oldItem = oldProductItems.getOrNull(position)

            when {
                oldItem == null -> addItem(newItem)
                !isContentTheSame(oldItem, newItem) -> replaceItem(position, newItem)
            }
        }
    }

    private fun isContentTheSame(
        oldItem: ProductsItem,
        newItem: ProductsItem,
    ): Boolean =
        when (oldItem) {
            is ProductsItem.ProductItem -> oldItem.value == (newItem as ProductsItem.ProductItem).value
            is ProductsItem.LoadMoreItem -> false
        }

    private fun replaceItem(
        position: Int,
        newItem: ProductsItem,
    ) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    private fun addItem(item: ProductsItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun removeExceedingItems(
        oldProductItems: List<ProductsItem>,
        newProductItems: List<ProductsItem.ProductItem>,
    ) {
        if (oldProductItems.size > newProductItems.size) {
            for (position in oldProductItems.lastIndex downTo newProductItems.size) {
                removeItem(position)
            }
        }
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun updateLoadMoreItem(hasMore: Boolean) {
        val loadMoreIndex = items.indexOfFirst { it is ProductsItem.LoadMoreItem }

        when {
            hasMore && loadMoreIndex == -1 -> addItem(ProductsItem.LoadMoreItem)
            hasMore && loadMoreIndex != -1 -> {
                removeItem(loadMoreIndex)
                addItem(ProductsItem.LoadMoreItem)
            }

            !hasMore && loadMoreIndex != -1 -> removeItem(loadMoreIndex)
        }
    }

    interface OnClickHandler :
        ProductViewHolder.OnClickHandler,
        LoadMoreViewHolder.OnClickHandler
}
