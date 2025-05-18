package woowacourse.shopping.ui.products

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.Product

class ProductsAdapter(
    private val onClickHandler: OnClickHandler,
) : RecyclerView.Adapter<ProductsItemViewHolder<ProductsItem, ViewDataBinding>>() {
    private val items: MutableList<ProductsItem> = mutableListOf<ProductsItem>()

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

    fun submitItems(newItems: List<Product>) {
        val newProductItems = newItems.map { ProductsItem.ProductItem(it) }
        val oldProductItems = items.toList()

        for ((position, newItem) in newProductItems.withIndex()) {
            val oldItem = oldProductItems.getOrNull(position)

            when {
                oldItem == null -> addItem(newItem)
                !isItemTheSame(oldItem, newItem) || !isContentTheSame(oldItem, newItem) -> updateItem(position, newItem)
            }
        }

        if (oldProductItems.size > newProductItems.size) {
            for (position in oldProductItems.lastIndex downTo newProductItems.size) {
                removeItem(position)
            }
        }

        items.removeAll { item -> item is ProductsItem.LoadMoreItem }
    }

    private fun isItemTheSame(
        oldItem: ProductsItem,
        newItem: ProductsItem,
    ): Boolean = oldItem.id == newItem.id

    private fun isContentTheSame(
        oldItem: ProductsItem,
        newItem: ProductsItem,
    ): Boolean =
        when (oldItem) {
            is ProductsItem.ProductItem -> oldItem.value == (newItem as ProductsItem.ProductItem).value
            is ProductsItem.LoadMoreItem -> true
        }

    private fun updateItem(
        position: Int,
        newItem: ProductsItem,
    ) {
        for (position in 0 until items.size) {
            items[position] = newItem
        }
        notifyItemRangeChanged(position, items.size - position)
    }

    private fun addItem(item: ProductsItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun removeItem(position: Int) {
        if (position in 0 until items.size) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateHasMoreItem(hasMore: Boolean) {
        if (hasMore) addItem(ProductsItem.LoadMoreItem)
    }

    interface OnClickHandler :
        ProductViewHolder.OnClickHandler,
        LoadMoreViewHolder.OnClickHandler
}
