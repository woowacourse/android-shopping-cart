package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductRepository {
    fun getProduct(productId: Long): Product

    fun getProductOrNull(productId: Long): Product?

    fun getProducts(): List<Product>
}
