package woowacourse.shopping.feature.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.list.viewholder.RecentItemViewHolder

class RecentProductsAdapter(
    private var items: List<ListItem>,
) : RecyclerView.Adapter<RecentItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecentBinding.inflate(inflater, parent, false)
        return RecentItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentItemViewHolder, position: Int) {
        holder.bind(items[position]) {}
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<ListItem>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
