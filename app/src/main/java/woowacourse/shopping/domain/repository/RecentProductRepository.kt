package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface RecentProductRepository {
    fun getRecentProducts(
        limit: Int,
        onResult: (Result<List<Product>>) -> Unit,
    )

    fun insertAndTrimToLimit(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteByProductId(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    )
}
