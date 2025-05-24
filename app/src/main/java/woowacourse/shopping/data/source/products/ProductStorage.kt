package woowacourse.shopping.data.source.products

import woowacourse.shopping.domain.Product

interface ProductStorage {
    fun getProducts(): List<Product>

    fun getProducts(
        currentIndex: Int,
        limit: Int,
    ): List<Product>

    fun getProductsSize(): Int
}
