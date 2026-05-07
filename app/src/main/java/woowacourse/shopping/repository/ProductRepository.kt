package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductRepository {
    val totalSize: Int

    fun getProduct(productId: String): Product?

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>
}
