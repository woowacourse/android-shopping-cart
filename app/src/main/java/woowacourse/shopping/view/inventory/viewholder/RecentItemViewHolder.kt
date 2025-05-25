package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryRecentItemBinding
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.view.base.BaseViewHolder

class RecentItemViewHolder(
    parent: ViewGroup,
) : BaseViewHolder<ItemInventoryRecentItemBinding>(
        ItemInventoryRecentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    fun bind(item: RecentProduct) {
        binding.product = item
    }
}
