package woowacourse.shopping.data.product

import woowacourse.shopping.Product

interface ProductRemoteDataSource {
    val products: List<Product>
    fun findProductById(id: Int): Product
    fun getProductsWithRange(startIndex: Int, size: Int): List<Product>
}
