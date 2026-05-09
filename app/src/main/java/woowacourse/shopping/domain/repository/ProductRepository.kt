package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>

    suspend fun getProduct(id: String): Product?
}
