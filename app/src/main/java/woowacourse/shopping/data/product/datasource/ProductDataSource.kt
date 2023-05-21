package woowacourse.shopping.data.product.datasource

import woowacourse.shopping.data.product.ProductEntity

interface ProductDataSource {

    fun getProductById(id: Int): ProductEntity

    fun getProductInRange(from: Int, count: Int): List<ProductEntity>
}
