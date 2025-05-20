package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product

interface ShoppingCartRepository {
    fun load(onLoad: (Result<List<Product>>) -> Unit)

    fun add(
        product: Product,
        onAdd: (Result<Unit>) -> Unit,
    )

    fun remove(
        product: Product,
        onRemove: (Result<Unit>) -> Unit,
    )
}
