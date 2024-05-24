package woowacourse.shopping.data.source

import woowacourse.shopping.data.ProductIdsCountData

interface ProductIdsCountDataSource {
    fun findByProductId(productId: Int): ProductIdsCountData

    fun loadAll(): List<ProductIdsCountData>

    fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Int

    fun removedProductsId(productId: Int): Int

    fun plusProductsIdCount(productId: Int)

    fun minusProductsIdCount(productId: Int)

    fun clearAll()
}
