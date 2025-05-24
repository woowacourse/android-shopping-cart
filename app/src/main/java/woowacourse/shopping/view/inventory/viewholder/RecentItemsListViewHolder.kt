package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryRecentListBinding
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.inventory.RecentListAdapter

class RecentItemsListViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<ItemInventoryRecentListBinding>(
        ItemInventoryRecentListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    fun bind() {
        binding.rvRecentList.adapter = RecentListAdapter()
    }
}
