package woowacourse.shopping.feature.main.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentAdapter(
    items: List<RecentProductItemModel>
) : RecyclerView.Adapter<RecentViewHolder>() {
    private val items = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        return RecentViewHolder.create(parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(newItems: List<RecentProductItemModel>) {
        val recentItem = newItems.firstOrNull() ?: return
        if (items.isEmpty()) {
            items.addAll(newItems)
            notifyItemRangeInserted(0, newItems.size)
        }
        val previousRecentItemIds = items.map { it.recentProduct.productUiModel.id }
        val findIndex = previousRecentItemIds.indexOf(recentItem.recentProduct.productUiModel.id)
        if (findIndex >= 0) {
            items.removeAt(findIndex)
            items.add(0, recentItem)
            notifyItemMoved(findIndex, 0)
        } else {
            items.add(0, recentItem)
            if (items.size > 10) items.removeLast()
            notifyItemInserted(0)
        }
    }
}
