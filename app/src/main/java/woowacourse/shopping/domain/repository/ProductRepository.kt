package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun getProductById(id: String): Product

    fun getProducts(): List<Product>
}
