package woowacourse.shopping.data.product.cache

import woowacourse.shopping.data.product.ProductEntity

interface ProductCache {

    fun getProductById(id: Int): ProductEntity

    fun getProductInRange(from: Int, count: Int): List<ProductEntity>
}
