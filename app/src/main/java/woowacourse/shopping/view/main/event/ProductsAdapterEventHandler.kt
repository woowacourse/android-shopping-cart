package woowacourse.shopping.view.main.event

import woowacourse.shopping.domain.Cart

interface ProductsAdapterEventHandler {
    fun onSelectProduct(productId: Long)

    fun onAddCart(cart: Cart)

    fun onQuantityMinus(cart: Cart)

    fun onQuantityPlus(cart: Cart)
}
