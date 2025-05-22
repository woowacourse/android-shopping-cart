package woowacourse.shopping.repository

import woowacourse.shopping.model.products.Product

interface ProductRepository {
    fun fetchAllProducts(): List<Product>

    fun productCount(): Int

    fun pageOfProducts(
        start: Int,
        end: Int,
    ): List<Product>
}
