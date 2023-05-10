package woowacourse.shopping.feature.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.list.viewholder.ItemHolder
import woowacourse.shopping.feature.list.viewholder.ProductViewHolder
import woowacourse.shopping.feature.list.viewholder.RecentListViewHolder
import woowacourse.shopping.feature.main.ViewType

class ProductListAdapter(
    private var items: List<ListItem> = listOf(),
    private var recentItems: List<ListItem>,
    private val onItemClick: (ListItem) -> Unit
) : RecyclerView.Adapter<ItemHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (ViewType.getType(position)) {
            ViewType.HORIZONTAL -> {
                ViewType.HORIZONTAL.ordinal
            }
            ViewType.PRODUCT -> {
                ViewType.PRODUCT.ordinal
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (ViewType.get(viewType)) {
            ViewType.HORIZONTAL -> {
                val binding = ItemRecentProductListBinding.inflate(inflater, parent, false)
                RecentListViewHolder(binding, recentItems)
            }
            ViewType.PRODUCT -> {
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                ProductViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    fun setItems(items: List<ListItem>) {
        this.items = items.toList()
        notifyDataSetChanged()
    }
}
