package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.model.CartedProduct

interface CartItemEventListener {
    fun onCartItemDelete(cartedProduct: CartedProduct)
}
