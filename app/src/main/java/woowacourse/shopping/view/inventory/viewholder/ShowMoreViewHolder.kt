package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryShowMoreBinding
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.inventory.InventoryEventHandler
import woowacourse.shopping.view.inventory.item.InventoryItem.ShowMore

class ShowMoreViewHolder(
    parent: ViewGroup,
    handler: InventoryEventHandler,
) : BaseViewHolder<ItemInventoryShowMoreBinding>(
        ItemInventoryShowMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false),
    ) {
    init {
        binding.handler = handler
    }

    fun bind(item: ShowMore) {
        binding.button = item
    }
}
