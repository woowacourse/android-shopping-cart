package woowacourse.shopping.data.storage

import woowacourse.shopping.domain.Product

interface ProductStorage {
    fun getProducts(): List<Product>

    fun getProducts(
        currentIndex: Int,
        limit: Int,
    ): List<Product>

    fun getProductsSize(): Int
}
