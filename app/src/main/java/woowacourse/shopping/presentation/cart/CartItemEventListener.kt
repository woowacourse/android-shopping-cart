package woowacourse.shopping.presentation.cart

import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.data.model.CartableProduct
import woowacourse.shopping.data.model.CartedProduct

interface CartItemEventListener {
    fun onCartItemDelete(cartedProduct: CartedProduct)
}
