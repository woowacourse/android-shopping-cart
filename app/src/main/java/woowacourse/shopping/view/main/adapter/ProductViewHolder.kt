package woowacourse.shopping.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.view.core.base.BaseViewHolder
import woowacourse.shopping.view.core.handler.CartQuantityHandler

class ProductViewHolder(
    parent: ViewGroup,
    private val handler: Handler,
    private val quantityHandler: CartQuantityHandler,
) : BaseViewHolder<ItemProductBinding>(
        binding =
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
    ) {
    fun bind(item: ProductRvItems.ProductItem) {
        with(binding) {
            model = item
            adapterEventHandler = handler
            cartQuantityEventHandler = quantityHandler
            executePendingBindings()
        }
    }

    interface Handler {
        fun onSelectProduct(productId: Long)

        fun showQuantity(productId: Long)
    }
}
