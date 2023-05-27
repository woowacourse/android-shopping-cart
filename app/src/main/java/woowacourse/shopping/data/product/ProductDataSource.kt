package woowacourse.shopping.data.product

import woowacourse.shopping.model.Product

interface ProductDataSource {
    fun findProductById(id: Long): Product?
    fun getProductsWithRange(startIndex: Int, size: Int): List<Product>
}
