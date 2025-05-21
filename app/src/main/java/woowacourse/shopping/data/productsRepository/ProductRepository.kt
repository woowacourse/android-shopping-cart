package woowacourse.shopping.data.productsRepository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun getProducts(): List<Product>

    fun getProducts(
        currentIndex: Int,
        limit: Int,
    ): List<Product>

    fun getProductsSize(): Int
}
