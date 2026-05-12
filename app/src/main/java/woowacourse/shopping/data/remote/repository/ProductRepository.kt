package woowacourse.shopping.data.remote.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    suspend fun getProduct(id: String): Product
}