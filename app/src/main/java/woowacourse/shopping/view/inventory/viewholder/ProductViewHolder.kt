package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryProductBinding
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.inventory.InventoryEventHandler
import woowacourse.shopping.view.inventory.item.InventoryItem.InventoryProduct

class ProductViewHolder(
    parent: ViewGroup,
    handler: InventoryEventHandler,
) : BaseViewHolder<ItemInventoryProductBinding>(
        ItemInventoryProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    init {
        binding.handler = handler
    }

    fun bind(item: InventoryProduct) {
        binding.product = item
    }
}
