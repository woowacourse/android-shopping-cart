package woowacourse.shopping.presentation.product

import woowacourse.shopping.domain.model.CartItem

interface ItemClickListener {
    fun onClickProductItem(productId: Long)

    fun onClickAddToCart(cartItem: CartItem)
}
