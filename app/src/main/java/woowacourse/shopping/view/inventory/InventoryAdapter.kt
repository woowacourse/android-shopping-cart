package woowacourse.shopping.view.inventory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.inventory.item.InventoryItem
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductUiModel
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore
import woowacourse.shopping.view.inventory.item.InventoryItemType
import woowacourse.shopping.view.inventory.viewholder.ProductViewHolder
import woowacourse.shopping.view.inventory.viewholder.ShowMoreViewHolder

class InventoryAdapter(
    private val handler: InventoryEventHandler,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items: MutableList<InventoryItem> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].type.id
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = items[position]
        when (holder) {
            is ProductViewHolder -> holder.bind(item as ProductUiModel)
            is ShowMoreViewHolder -> holder.bind(item as ShowMore)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            InventoryItemType.PRODUCT.id -> ProductViewHolder(parent, handler)
            InventoryItemType.SHOW_MORE.id -> ShowMoreViewHolder(parent, handler)
            else -> throw IllegalStateException()
        }
    }

    fun updateProducts(newItems: List<InventoryItem>) {
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
    }
}
