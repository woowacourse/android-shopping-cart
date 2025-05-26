package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.domain.product.CartItem

interface DetailClickListener {
    fun onAddToCartClick(cartItem: CartItem)

    fun onRecentProductClick()
}
