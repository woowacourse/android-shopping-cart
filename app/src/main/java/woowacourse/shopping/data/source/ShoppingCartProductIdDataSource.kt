package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductIdsCountData

interface ShoppingCartProductIdDataSource {
    fun findByProductId(productId: Long): ProductIdsCountData?

    fun loadPaged(page: Int): List<ProductIdsCountData>

    fun loadAll(): List<ProductIdsCountData>

    fun isFinalPage(page: Int): Boolean

    fun addedNewProductsId(productIdsCountData: ProductIdsCountData): Long

    fun removedProductsId(productId: Long): Long

    fun plusProductsIdCount(productId: Long)

    fun minusProductsIdCount(productId: Long)

    fun clearAll()

    // async function with callback
    fun findByProductIdAsync(
        productId: Long,
        callback: (ProductIdsCountData?) -> Unit,
    )

    fun loadPagedAsync(
        page: Int,
        callback: (List<ProductIdsCountData>) -> Unit,
    )

    fun loadAllAsync(callback: (List<ProductIdsCountData>) -> Unit)

    fun isFinalPageAsync(
        page: Int,
        callback: (Boolean) -> Unit,
    )

    fun addedNewProductsIdAsync(
        productIdsCountData: ProductIdsCountData,
        callback: (Long) -> Unit,
    )

    fun removedProductsIdAsync(
        productId: Long,
        callback: (Long) -> Unit,
    )

    fun plusProductsIdCountAsync(productId: Long)

    fun minusProductsIdCountAsync(productId: Long)

    fun clearAllAsync()
}
