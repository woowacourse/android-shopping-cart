package woowacourse.shopping.data.products

import woowacourse.shopping.model.product.Product

interface ProductRepository {
    val dummyProducts: List<Product>

    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<Product>
}
