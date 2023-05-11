package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentProductListBinding
import woowacourse.shopping.feature.list.adapter.RecentProductListAdapter
import woowacourse.shopping.feature.list.item.ListItem

class RecentListItemViewHolder(binding: ViewBinding, val items: List<ListItem>) : ItemHolder(binding) {
    private val binding = binding as ItemRecentProductListBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        val adapter = RecentProductListAdapter(items)
        binding.recentListRv.adapter = adapter
    }
}
