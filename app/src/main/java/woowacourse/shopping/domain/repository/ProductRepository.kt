package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun loadProducts(
        lastItemId: Long,
        loadSize: Int,
        callback: (List<Product>, Boolean) -> Unit,
    )

    fun loadCartItems(callback: (List<CartItem>?) -> Unit)

    fun addRecentProduct(product: Product)

    fun loadRecentProducts(
        limit: Int,
        callback: (List<Product>) -> Unit,
    )

    fun loadLastViewedProduct(
        currentProductId: Long,
        callback: (Product?) -> Unit,
    )

    fun getProductById(
        id: Long,
        callback: (Product?) -> Unit,
    )
}
