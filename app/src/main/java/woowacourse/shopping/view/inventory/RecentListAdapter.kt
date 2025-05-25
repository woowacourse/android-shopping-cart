package woowacourse.shopping.view.inventory

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.view.inventory.item.RecentProduct
import woowacourse.shopping.view.inventory.viewholder.RecentItemViewHolder

class RecentListAdapter : RecyclerView.Adapter<RecentItemViewHolder>() {
    private val items: MutableList<RecentProduct> = mutableListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentItemViewHolder {
        return RecentItemViewHolder(parent)
    }

    override fun onBindViewHolder(
        holder: RecentItemViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    fun submitList(newItems: List<RecentProduct>) {
        items.clear()
        items.addAll(newItems)
    }
}
