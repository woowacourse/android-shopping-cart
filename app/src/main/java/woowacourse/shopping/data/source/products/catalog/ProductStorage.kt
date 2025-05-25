package woowacourse.shopping.data.source.products.catalog

import woowacourse.shopping.domain.Product

interface ProductStorage {
    fun getProducts(): List<Product>

    fun getProducts(
        currentIndex: Int,
        limit: Int,
    ): List<Product>

    fun getProductsSize(): Int

    fun getProduct(productId: Long): Product
}
