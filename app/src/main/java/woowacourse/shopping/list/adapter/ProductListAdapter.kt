package woowacourse.shopping.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemMoreBinding
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.list.ViewType
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.viewholder.ItemHolder
import woowacourse.shopping.list.viewholder.ProductMoreViewHolder
import woowacourse.shopping.list.viewholder.ProductViewHolder
import woowacourse.shopping.list.viewholder.RecentListItemViewHolder

class ProductListAdapter(
    private var items: List<ListItem> = listOf(),
    private var recentItems: List<ListItem> = listOf(),
    private val onItemClick: (ListItem) -> Unit,
    private val onMoreItemClick: (ListItem) -> Unit,
) : RecyclerView.Adapter<ItemHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (ViewType.get(viewType)) {
            ViewType.RECENT_PRODUCT -> {
                val binding = ItemRecentProductListBinding.inflate(inflater, parent, false)
                RecentListItemViewHolder(binding, recentItems)
            }
            ViewType.PRODUCT -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ProductViewHolder(binding)
            }
            ViewType.LOAD_MORE -> {
                val binding = ItemMoreBinding.inflate(inflater, parent, false)
                ProductMoreViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        when (holder) {
            is ProductMoreViewHolder -> {
                holder.bind(items[position], onMoreItemClick)
            }
            else -> {
                holder.bind(items[position], onItemClick)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return ViewType.getType(position).ordinal
    }

    fun addItems(newItems: List<ListItem>) {
        val items = this.items.toMutableList()
        newItems.forEach {
            items.add(it)
        }
        this.items = items.toList()
        notifyItemRangeChanged(items.size, newItems.size)
    }

    fun setItems(items: List<ListItem>, recentItems: List<ListItem>) {
        this.items = items.toList()
        this.recentItems = recentItems.toList()
        notifyDataSetChanged()
    }
}
