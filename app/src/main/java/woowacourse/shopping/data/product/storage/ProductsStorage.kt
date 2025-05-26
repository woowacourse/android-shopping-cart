package woowacourse.shopping.data.product.storage

import woowacourse.shopping.data.product.local.entity.ProductEntity

interface ProductsStorage {
    fun load(
        lastProductId: Long?,
        size: Int,
    ): List<ProductEntity>
}
