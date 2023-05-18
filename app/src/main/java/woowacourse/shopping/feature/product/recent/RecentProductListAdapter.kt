package woowacourse.shopping.feature.product.recent

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.common.ViewType
import woowacourse.shopping.model.RecentProductState

class RecentProductListAdapter(
    private var recentProductStates: List<RecentProductState> = listOf(),
) : RecyclerView.Adapter<RecentProductItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductItemViewHolder {
        return RecentProductItemViewHolder.createInstance(parent)
    }

    override fun onBindViewHolder(holder: RecentProductItemViewHolder, position: Int) {
        holder.bind(recentProductStates[position])
    }

    override fun getItemCount(): Int = recentProductStates.size

    override fun getItemViewType(position: Int): Int = ViewType.RECENT_PRODUCT.ordinal

    fun addItems(newItems: List<RecentProductState>) {
        val items = this.recentProductStates.toMutableList()
        newItems.forEach { items.add(0, it) }
        this.recentProductStates = items.toList()
        notifyItemRangeInserted(recentProductStates.size, newItems.size)
    }

    fun setItems(items: List<RecentProductState>) {
        this.recentProductStates = items.toList()
        notifyDataSetChanged()
    }
}
