package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.view.base.BaseViewHolder

class ShoppingCartViewHolder(
    parent: ViewGroup,
    private val handler: ShoppingCartEventHandler,
) : BaseViewHolder<ItemShoppingCartProductBinding>(
        ItemShoppingCartProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        ),
    ) {
    init {
        binding.handler = handler
    }

    fun bind(cartProduct: CartProduct) {
        binding.cartItem = cartProduct
        binding.tvIncreaseQuantity.setOnClickListener {
            handler.onIncreaseQuantity(adapterPosition, cartProduct)
        }
        binding.tvDecreaseQuantity.setOnClickListener {
            handler.onDecreaseQuantity(adapterPosition, cartProduct)
        }
        binding.ivRemoveItemProductIcon.setOnClickListener {
            handler.onRemoveCartItem(cartProduct)
        }
    }
}
