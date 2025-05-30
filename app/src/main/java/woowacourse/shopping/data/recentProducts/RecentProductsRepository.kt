package woowacourse.shopping.data.recentProducts

import woowacourse.shopping.model.product.Product

interface RecentProductsRepository {
    fun getAll(callback: (Result<List<Product>>) -> Unit)

    fun add(
        product: Product,
        callback: (Result<Unit>) -> Unit,
    )

    fun remove(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun getSecondMostRecentProduct(callback: (Result<Product>) -> Unit)

    fun update(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun findRecentProductById(
        productId: Long,
        callback: (Result<Product>) -> Unit,
    )
}
