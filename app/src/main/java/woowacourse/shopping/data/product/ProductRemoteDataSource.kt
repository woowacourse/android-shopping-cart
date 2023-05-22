package woowacourse.shopping.data.product

import woowacourse.shopping.Product

interface ProductRemoteDataSource {
    fun findProductById(id: Int): Product
    fun getProductsWithRange(startIndex: Int, size: Int): List<Product>
}
