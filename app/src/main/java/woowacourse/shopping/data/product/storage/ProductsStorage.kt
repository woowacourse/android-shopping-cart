package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.entity.ProductEntity

interface ProductsStorage {
    val lastProductId: Long?
    fun load(lastProductId: Long?, size: Int): List<ProductEntity>
}
