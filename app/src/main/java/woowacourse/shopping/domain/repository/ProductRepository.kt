package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    val hasNext: Boolean

    fun getProductById(id: String): Product

    fun getProducts(): List<Product>
}
