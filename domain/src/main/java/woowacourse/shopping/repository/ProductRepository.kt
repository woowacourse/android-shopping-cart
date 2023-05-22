package woowacourse.shopping.repository

import woowacourse.shopping.Product

interface ProductRepository {
    fun findProductById(id: Int): Product?
    fun getProductsWithRange(startIndex: Int, size: Int): List<Product>
}
