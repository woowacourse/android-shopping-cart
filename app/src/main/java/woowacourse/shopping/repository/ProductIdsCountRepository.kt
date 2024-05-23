package woowacourse.shopping.repository

import woowacourse.shopping.domain.model.ProductIdsCount

interface ProductIdsCountRepository {
    fun findByProductId(productId: Int): ProductIdsCount

    fun loadAllProductIdsCounts(): List<ProductIdsCount>

    fun addedProductsId(productIdsCount: ProductIdsCount): Int

    fun removedProductsId(productId: Int): Int

    fun plusProductsIdCount(productId: Int)

    fun minusProductsIdCount(productId: Int)

    fun clearAll()
}
