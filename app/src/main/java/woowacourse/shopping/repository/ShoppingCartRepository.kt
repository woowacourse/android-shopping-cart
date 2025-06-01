package woowacourse.shopping.repository

import woowacourse.shopping.model.products.ShoppingCart

interface ShoppingCartRepository {
    fun addCart(
        productId: Int,
        quantity: Int,
    )

    fun removeCart(productId: Int)

    fun singlePage(
        page: Int,
        size: Int,
    ): List<ShoppingCart>
}
