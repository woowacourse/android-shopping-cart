package woowacourse.shopping.view.detail

import woowacourse.shopping.domain.CartItem

interface ProductDetailEventHandler {
    fun onAddToCartSelected(cartItem: CartItem)
}
