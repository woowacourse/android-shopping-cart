package woowacourse.shopping.repository.product

import woowacourse.shopping.domain.product.Product

interface ProductRepository {
    suspend fun getProductsSize(): Int

    suspend fun getProduct(id: String): Product

    suspend fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
