package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.model.cart.CartedProduct

interface CartItemEventListener {
    fun onCartItemDelete(cartedProduct: CartedProduct)
}
