package woowacourse.shopping.presentation.product

import woowacourse.shopping.domain.model.CartItem

interface ItemClickListener {
    fun onClickProductItem(cartItem: CartItem)

    fun onClickAddToCart(cartItem: CartItem)
}
