package woowacourse.shopping.view.inventory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem
import woowacourse.shopping.view.inventory.item.InventoryItem.RecentProductsItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore
import woowacourse.shopping.view.inventory.item.InventoryItem.ViewType
import woowacourse.shopping.view.inventory.viewholder.InventoryViewHolder

class InventoryAdapter(
    private val handler: InventoryEventHandler,
) : RecyclerView.Adapter<InventoryViewHolder<*>>() {
    private val items: MutableList<InventoryItem> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type.ordinal
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
            is InventoryViewHolder.ProductViewHolder -> holder.bind(item as ProductItem)
            is InventoryViewHolder.ShowMoreViewHolder -> holder.bind(item as ShowMore)
            is InventoryViewHolder.RecentItemsListViewHolder -> holder.bind(item as RecentProductsItem)
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
            for (removedIndex in newCount..<oldCount) {
                items.removeAt(items.size - 1)
            }
            notifyItemRangeRemoved(newCount, oldCount - newCount)
        }
    }
}
