package woowacourse.shopping.database.product.datasource

import woowacourse.shopping.database.product.ProductEntity

interface ProductDataSource {

    fun getProductById(id: Int): ProductEntity

    fun getProductInRange(from: Int, count: Int): List<ProductEntity>
}
