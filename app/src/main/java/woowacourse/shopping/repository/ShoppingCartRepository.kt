package woowacourse.shopping.repository

import woowacourse.shopping.model.products.ShoppingCart

interface ShoppingCartRepository {
    fun updateCart(
        productId: Int,
        quantity: Int,
    )

    fun removeCart(productId: Int)

    fun singlePage(
        page: Int,
        size: Int,
        onResult: (List<ShoppingCart>) -> Unit,
    )
}
