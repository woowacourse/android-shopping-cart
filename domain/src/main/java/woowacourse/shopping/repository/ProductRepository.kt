package woowacourse.shopping.repository

import woowacourse.shopping.Product

interface ProductRepository {
    val products: List<Product>
    fun findProductById(id: Int): Product?
    fun getProductsWithRange(startIndex: Int, size: Int): List<Product>
}
