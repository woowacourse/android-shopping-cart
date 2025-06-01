package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface RecentProductRepository {
    fun getRecentProducts(onResult: (Result<List<Product>>) -> Unit)

    fun getMostRecentProduct(onResult: (Result<Product?>) -> Unit)

    fun insertRecentProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )
}
