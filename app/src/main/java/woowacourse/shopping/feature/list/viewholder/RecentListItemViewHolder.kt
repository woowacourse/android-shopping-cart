package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.feature.list.adapter.RecentProductsAdapter
import woowacourse.shopping.feature.list.item.ListItem

class RecentListItemViewHolder(binding: ViewBinding, val items: List<ListItem>) : ItemViewHolder(binding) {
    private val binding = binding as ItemRecentProductListBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        val adapter = RecentProductsAdapter(items)
        binding.recentListRv.adapter = adapter
    }
}
