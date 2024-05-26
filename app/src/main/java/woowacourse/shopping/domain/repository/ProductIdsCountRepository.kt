package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.ProductIdsCount

interface ProductIdsCountRepository {
    fun findByProductId(productId: Long): ProductIdsCount

    fun loadAllProductIdsCounts(): List<ProductIdsCount>

    fun addedProductsId(productIdsCount: ProductIdsCount): Long

    fun removedProductsId(productId: Long): Long

    fun plusProductsIdCount(productId: Long)

    fun minusProductsIdCount(productId: Long)

    fun clearAll()
}
