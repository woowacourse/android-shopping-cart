package woowacourse.shopping.domain.product.repository

import woowacourse.shopping.domain.product.model.Product

interface ProductRepository {
    suspend fun getProductsSize(): Int

    suspend fun getProduct(id: String): Product

    suspend fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
