package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products

interface ProductRepository {
    fun getProductById(id: String): Product

    fun getProductsByIds(ids: List<String>): List<Product>

    suspend fun getProducts(offset: Int): Products
}
