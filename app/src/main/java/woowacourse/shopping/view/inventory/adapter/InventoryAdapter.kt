package woowacourse.shopping.view.inventory.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.inventory.InventoryEventHandler
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ProductUiModel
import woowacourse.shopping.view.inventory.adapter.InventoryItem.RecentProducts
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ShowMore
import woowacourse.shopping.view.inventory.adapter.InventoryItem.ViewType

class InventoryAdapter(
    private val handler: InventoryEventHandler,
) : RecyclerView.Adapter<InventoryViewHolder<*>>() {
    private val items: MutableList<InventoryItem> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.ordinal
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): InventoryViewHolder<*> {
        return when (ViewType.entries[viewType]) {
            ViewType.PRODUCT -> InventoryViewHolder.ProductViewHolder(parent, handler)
            ViewType.SHOW_MORE -> InventoryViewHolder.ShowMoreViewHolder(parent, handler)
            ViewType.RECENT_PRODUCTS -> InventoryViewHolder.RecentItemsListViewHolder(parent, handler)
        }
    }

    override fun onBindViewHolder(
        holder: InventoryViewHolder<*>,
        position: Int,
    ) {
        val item = items[position]
        when (holder) {
            is InventoryViewHolder.ProductViewHolder -> holder.bind(item as ProductUiModel)
            is InventoryViewHolder.ShowMoreViewHolder -> holder.bind(item as ShowMore)
            is InventoryViewHolder.RecentItemsListViewHolder -> holder.bind(item as RecentProducts)
        }
    }

    fun submitList(newItems: List<InventoryItem>) {
        val oldCount = items.size
        val newCount = newItems.size

        newItems.forEachIndexed { index, newItem ->
            val oldItem = items.getOrNull(index)
            if (oldItem == null) {
                items.add(newItem)
                notifyItemInserted(index)
            } else if (oldItem != newItem) {
                items[index] = newItem
                notifyItemChanged(index)
            }
        }

        if (newCount < oldCount) {
            repeat(oldCount - newCount) {
                items.removeAt(items.size - 1)
            }
            notifyItemRangeRemoved(newCount, oldCount - newCount)
        }
    }

    fun updateProduct(product: ProductUiModel) {
        items.forEachIndexed { index, item ->
            if (item is ProductUiModel && item.product.id == product.product.id) items[index] = product
            notifyItemChanged(index)
        }
    }
}
