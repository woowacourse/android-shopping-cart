package woowacourse.shopping.view.shoppingcart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.view.base.BaseViewHolder
import woowacourse.shopping.view.shoppingcart.ShoppingCartEventHandler

class ShoppingCartViewHolder(
    parent: ViewGroup,
    handler: ShoppingCartEventHandler,
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

    fun bind(product: CartProduct) {
        binding.item = product
    }
}
