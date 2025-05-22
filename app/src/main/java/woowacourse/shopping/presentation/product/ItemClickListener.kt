package woowacourse.shopping.presentation.product

import woowacourse.shopping.domain.model.CartItem

interface ItemClickListener {
    fun onAddToCartClick(cartItem: CartItem)
}
