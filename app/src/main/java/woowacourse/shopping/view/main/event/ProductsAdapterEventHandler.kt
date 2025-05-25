package woowacourse.shopping.view.main.event

import woowacourse.shopping.domain.Cart

interface ProductsAdapterEventHandler {
    fun onSelectProduct(cart: Cart)

    fun onAddCart(cart: Cart)
}
