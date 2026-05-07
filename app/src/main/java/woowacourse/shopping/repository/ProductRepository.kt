package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductRepository {
    fun getProduct(productId: String): Product?

    fun getProducts(): List<Product>
}
