package woowacourse.shopping.ui.catalog.adapter.product

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.CatalogProduct

class CatalogAdapter(
    private val onClickHandler: OnClickHandler,
) : RecyclerView.Adapter<CatalogItemViewHolder<CatalogItem, ViewDataBinding>>() {
    private val items: MutableList<CatalogItem> = mutableListOf()

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CatalogItemViewHolder<CatalogItem, ViewDataBinding> =
        when (CatalogItemViewType.entries[viewType]) {
            CatalogItemViewType.PRODUCT -> ProductViewHolder(parent, onClickHandler)
            CatalogItemViewType.LOAD_MORE -> LoadMoreViewHolder(parent, onClickHandler)
        } as CatalogItemViewHolder<CatalogItem, ViewDataBinding>

    override fun onBindViewHolder(
        holder: CatalogItemViewHolder<CatalogItem, ViewDataBinding>,
        position: Int,
    ) {
        val catalogItem: CatalogItem = items[position]
        holder.bind(catalogItem)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].viewType.ordinal

    fun submitItems(
        newItems: List<CatalogProduct>,
        hasMore: Boolean,
    ) {
        val newProductItems = newItems.map { CatalogItem.ProductItem(it) }
        val oldProductItems = items.toList()

        updateNewItems(newProductItems, oldProductItems)
        removeExceedingItems(oldProductItems, newProductItems)
        updateLoadMoreItem(hasMore)
    }

    private fun updateNewItems(
        newProductItems: List<CatalogItem.ProductItem>,
        oldProductItems: List<CatalogItem>,
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
        oldItem: CatalogItem,
        newItem: CatalogItem,
    ): Boolean =
        when (oldItem) {
            is CatalogItem.ProductItem -> oldItem.value == (newItem as CatalogItem.ProductItem).value
            is CatalogItem.LoadMoreItem -> false
        }

    private fun replaceItem(
        position: Int,
        newItem: CatalogItem,
    ) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    private fun addItem(item: CatalogItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    private fun removeExceedingItems(
        oldProductItems: List<CatalogItem>,
        newProductItems: List<CatalogItem.ProductItem>,
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
        val loadMoreIndex = items.indexOfFirst { it is CatalogItem.LoadMoreItem }

        when {
            hasMore && loadMoreIndex == -1 -> {
                addItem(CatalogItem.LoadMoreItem)
            }

            hasMore && loadMoreIndex != -1 -> {
                removeItem(loadMoreIndex)
                addItem(CatalogItem.LoadMoreItem)
            }

            !hasMore && loadMoreIndex != -1 -> {
                removeItem(loadMoreIndex)
            }
        }
    }

    interface OnClickHandler :
        ProductViewHolder.OnClickHandler,
        LoadMoreViewHolder.OnClickHandler
}
