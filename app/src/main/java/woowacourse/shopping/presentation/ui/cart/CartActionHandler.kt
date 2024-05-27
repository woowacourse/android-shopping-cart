package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.presentation.ui.shopping.ShoppingActionHandler

interface CartActionHandler : ShoppingActionHandler {
    fun onDelete(cartProduct: CartProduct)

    fun onNext()

    fun onPrevious()
}
