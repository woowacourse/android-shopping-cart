package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryRecentItemBinding
import woowacourse.shopping.domain.RecentItem
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.inventory.InventoryEventHandler

class RecentItemViewHolder(
    parent: ViewGroup,
    handler: InventoryEventHandler,
) : BaseViewHolder<ItemInventoryRecentItemBinding>(
        ItemInventoryRecentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    init {
        binding.handler = handler
    }

    fun bind(item: RecentItem) {
        binding.product = item
    }
}
