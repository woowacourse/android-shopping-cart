package woowacourse.shopping.view.products

import woowacourse.shopping.model.cart.CartItem

interface ProductEventListener {
    fun onProductClick(item: CartItem)

    fun onOpenQuantitySelectClick(item: CartItem)
}
