package woowacourse.shopping.view.inventory.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemInventoryProductBinding
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.inventory.InventoryEventHandler
import woowacourse.shopping.view.inventory.item.InventoryItem.ProductItem

class ProductViewHolder(
    parent: ViewGroup,
    private val handler: InventoryEventHandler,
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

    fun bind(item: ProductItem) {
        binding.item = item
        binding.tvDecreaseQuantity.setOnClickListener {
            handler.onDecreaseQuantity(adapterPosition, item)
        }
        binding.tvIncreaseQuantity.setOnClickListener {
            handler.onIncreaseQuantity(adapterPosition, item)
        }
        binding.ivAddProductIcon.setOnClickListener {
            handler.onIncreaseQuantity(adapterPosition, item)
        }
    }
}
