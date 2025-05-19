package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product

interface ShoppingCartRepository {
    fun load(
        offset: Int,
        limit: Int,
        result: (Result<List<Product>>) -> Unit,
    )

    fun add(
        product: Product,
        result: (Result<Unit>) -> Unit,
    )

    fun remove(
        product: Product,
        result: (Result<Unit>) -> Unit,
    )
}
