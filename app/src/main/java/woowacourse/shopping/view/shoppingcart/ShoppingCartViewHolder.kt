package woowacourse.shopping.view.shoppingcart

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.shopping.databinding.ItemShoppingCartProductBinding
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.base.BaseViewHolder

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

    fun bind(item: Product) {
        binding.product = item
    }
}
