package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductIdsCountData

interface ShoppingCartProductIdDataSource {
    fun findByProductId(productId: Int): ProductIdsCountData?

    fun loadAll(): List<ProductIdsCountData>

    fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Int

    fun removedProductsId(productId: Int): Int

    fun plusProductsIdCount(productId: Int)

    fun minusProductsIdCount(productId: Int)

    fun clearAll()
}
