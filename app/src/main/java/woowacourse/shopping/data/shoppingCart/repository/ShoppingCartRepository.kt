package woowacourse.shopping.data.shoppingCart.repository

import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.shoppingCart.ShoppingCartProduct

interface ShoppingCartRepository {
    fun load(
        offset: Int,
        limit: Int,
        onResult: (Result<List<ShoppingCartProduct>>) -> Unit,
    )

    fun add(
        product: Product,
        quantity: Int,
        onResult: (Result<Unit>) -> Unit,
    )

    fun decreaseQuantity(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun remove(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun fetchSelectedQuantity(
        product: Product,
        onResult: (Result<Int?>) -> Unit,
    )

    fun fetchSelectedQuantity(
        products: List<Product>,
        onResult: (Result<List<ShoppingCartProduct>>) -> Unit,
    )

    fun fetchAllQuantity(onResult: (Result<Int>) -> Unit)
}
