package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct

interface CartItemEventListener {
    fun onCartItemDelete(cartableProduct: CartableProduct)
}
