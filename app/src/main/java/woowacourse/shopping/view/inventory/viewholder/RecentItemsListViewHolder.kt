package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryRecentListBinding
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.inventory.RecentListAdapter
import woowacourse.shopping.view.inventory.item.InventoryItem.RecentProductsItem

class RecentItemsListViewHolder(parent: ViewGroup) : BaseViewHolder<ItemInventoryRecentListBinding>(
    ItemInventoryRecentListBinding.inflate(LayoutInflater.from(parent.context), parent, false),
) {
    fun bind(item: RecentProductsItem) {
        RecentListAdapter().let { adapter ->
            binding.item = item
            binding.rvRecentList.adapter = adapter
            adapter.submitList(item.recentProducts)
        }
    }
}
