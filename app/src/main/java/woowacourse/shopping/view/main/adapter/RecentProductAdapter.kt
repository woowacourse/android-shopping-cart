package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentProductBinding
import woowacourse.shopping.domain.RecentProduct

class RecentProductAdapter(
    private var items: List<RecentProduct> = listOf(),
) : RecyclerView.Adapter<RecentProductViewHolder>() {
    fun submitList(newItems: List<RecentProduct>) {
        val oldSize = items.size
        items = newItems
        if (newItems.size == oldSize) {
            notifyItemRangeChanged(0, newItems.size)
        } else {
            notifyItemRangeRemoved(0, oldSize)
            notifyItemRangeInserted(0, newItems.size)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecentProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bind = ItemRecentProductBinding.inflate(inflater, parent, false)
        return RecentProductViewHolder(bind)
    }

    override fun onBindViewHolder(
        holder: RecentProductViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
