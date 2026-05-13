package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>

    suspend fun getProduct(id: String): Result<Product?>
}
