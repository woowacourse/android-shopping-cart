package woowacourse.shopping.domain

import woowacourse.shopping.domain.product.model.Product

interface RecentProductRepository {
    suspend fun addRecentProduct(productId: String)

    suspend fun getAllRecentProducts(): List<Product>
}
