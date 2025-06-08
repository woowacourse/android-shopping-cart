package woowacourse.shopping.view.inventory.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.domain.RecentItem
import woowacourse.shopping.view.inventory.InventoryEventHandler

class RecentListAdapter(private val handler: InventoryEventHandler) : RecyclerView.Adapter<RecentItemViewHolder>() {
    private val items: MutableList<RecentItem> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentItemViewHolder {
        return RecentItemViewHolder(parent, handler)
    }

    override fun onBindViewHolder(
        holder: RecentItemViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun submitList(newItems: List<RecentItem>) {
        items.clear()
        items.addAll(newItems)
    }
}
