package woowacourse.shopping.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.list.ViewType
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.viewholder.RecentProductItemViewHolder

class RecentProductListAdapter(
    private var items: List<ListItem> = listOf(),
) : RecyclerView.Adapter<RecentProductItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentProductItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentBinding.inflate(inflater, parent, false)
        return RecentProductItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentProductItemViewHolder, position: Int) {
        holder.bind(items[position]) { }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = ViewType.RECENT_PRODUCT.ordinal

    fun addItems(newItems: List<ListItem>) {
        val items = this.items.toMutableList()
        items.addAll(newItems)
        this.items = items.toList()
        notifyItemRangeInserted(items.size, newItems.size)
    }

    fun setItems(items: List<ListItem>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
