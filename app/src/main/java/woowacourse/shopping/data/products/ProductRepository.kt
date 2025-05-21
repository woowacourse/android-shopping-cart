package woowacourse.shopping.data.products

import woowacourse.shopping.model.product.Product

interface ProductRepository {
    fun getAll(): List<Product>

    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<Product>
}
