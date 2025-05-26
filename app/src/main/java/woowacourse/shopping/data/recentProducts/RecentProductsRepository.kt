package woowacourse.shopping.data.recentProducts

import woowacourse.shopping.model.product.Product

interface RecentProductsRepository {
    fun getAll(callback: (List<Product>) -> Unit)

    fun add(product: Product)

    fun remove(
        productId: Long,
        callback: () -> Unit,
    )

    fun getSecondMostRecentProduct(callback: (Product) -> Unit)

    fun getMostRecentProduct(callback: (Product) -> Unit)

    fun update(productId: Long)

    fun findRecentProductById(
        productId: Long,
        callback: (Product) -> Unit,
    )
}
