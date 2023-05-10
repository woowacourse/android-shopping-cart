package woowacourse.shopping.data.product

import woowacourse.shopping.Product

interface ProductRepository {
    val products: List<Product>
    fun findProductById(id: Int): Product?
}
