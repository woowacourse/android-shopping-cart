package woowacourse.shopping.list.viewholder

import androidx.viewbinding.ViewBinding
import woowacourse.shopping.databinding.ItemRecentBinding
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.item.RecentProductListItem
import woowacourse.shopping.model.mapper.toUi

class RecentProductItemViewHolder(binding: ViewBinding) : ItemHolder(binding) {
    val binding = binding as ItemRecentBinding

    override fun bind(listItem: ListItem, onClick: (ListItem) -> Unit) {
        listItem as RecentProductListItem

        binding.recentProduct = listItem.toUi()
    }
}
