package woowacourse.shopping.presentation.shopping.product

import woowacourse.shopping.presentation.cart.CartItemListener

interface ProductItemListener : CartItemListener {
    fun navigateToDetail(id: Long)

    fun loadProducts()
}

fun interface RecentProductItemListener {
    fun navigateToDetail(id: Long)
}
