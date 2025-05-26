package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryRecentItemBinding
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.inventory.InventoryEventHandler

class RecentItemViewHolder(
    parent: ViewGroup,
    private val handler: InventoryEventHandler,
) : BaseViewHolder<ItemInventoryRecentItemBinding>(
        ItemInventoryRecentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    fun bind(item: RecentProduct) {
        binding.product = item
        binding.handler = handler
    }
}
