package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product

interface ShoppingCartRepository {
    fun load(
        offset: Int,
        limit: Int,
        onResult: (Result<List<Product>>) -> Unit,
    )

    fun add(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun addWithQuantity(
        product: Product,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun remove(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )
}
