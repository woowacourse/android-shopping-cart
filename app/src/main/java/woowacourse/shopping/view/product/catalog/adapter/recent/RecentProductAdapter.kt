package woowacourse.shopping.view.product.catalog.adapter.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.model.RecentProduct

class RecentProductAdapter(
    items: List<RecentProduct> = emptyList(),
    private val eventHandler: RecentProductViewHolder.EventHandler,
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    private val items = items.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder = RecentProductViewHolder.from(parent, eventHandler)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun updateItems(newItems: List<RecentProduct>) {
        val oldSize = items.size
        val newSize = newItems.size
        val minSize = minOf(oldSize, newSize)

        for (i in 0 until minSize) {
            val oldItem = items[i]
            val newItem = newItems[i]

            if (oldItem != newItem) {
                items[i] = newItem
                notifyItemChanged(i)
            }
        }

        if (newSize > oldSize) {
            val addedItems = newItems.subList(oldSize, newSize)
            items.addAll(addedItems)
            notifyItemRangeInserted(oldSize, addedItems.size)
        }
    }
}
