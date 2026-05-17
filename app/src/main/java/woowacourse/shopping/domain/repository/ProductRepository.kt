package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product

interface ProductRepository {
    suspend fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    suspend fun getProduct(id: Int): Product?
}
