package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product

interface ShoppingCartRepository {
    val hasNext: Boolean

    val hasPrevious: Boolean

    fun load(page: Int, count: Int, result: (products: Result<List<Product>>) -> Unit)

    fun add(
        product: Product,
        result: (Result<Unit>) -> Unit
    )

    fun remove(
        product: Product,
        result: (Result<Unit>) -> Unit
    )
}
