package woowacourse.shopping.database.product.cache

import woowacourse.shopping.database.product.ProductEntity

interface ProductCache {

    fun getProductById(id: Int): ProductEntity

    fun getProductInRange(from: Int, count: Int): List<ProductEntity>
}
