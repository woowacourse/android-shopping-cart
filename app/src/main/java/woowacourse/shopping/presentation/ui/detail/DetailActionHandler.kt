package woowacourse.shopping.presentation.ui.detail

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

interface DetailActionHandler: ShoppingActionHandler {

    fun onAddToCart(cartProduct: CartProduct)
}