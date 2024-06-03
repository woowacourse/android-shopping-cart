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

    fun plusProductsIdCountAsync(
        productId: Long,
        callback: () -> Unit,
    )

    fun minusProductsIdCountAsync(
        productId: Long,
        callback: () -> Unit,
    )

    fun clearAllAsync()

    // async function with Result
    fun findByProductIdAsyncResult(
        productId: Long,
        callback: (Result<ProductIdsCountData?>) -> Unit,
    )

    fun findByProductIdAsyncResultNonNull(
        productId: Long,
        callback: (Result<ProductIdsCountData>) -> Unit
    )

    fun loadPagedAsyncResult(
        page: Int,
        callback: (Result<List<ProductIdsCountData>>) -> Unit,
    )

    fun loadAllAsyncResult(
        callback: (Result<List<ProductIdsCountData>>) -> Unit,
    )

    fun isFinalPageAsyncResult(
        page: Int,
        callback: (Result<Boolean>) -> Unit,
    )

    fun addedNewProductsIdAsyncResult(
        productIdsCountData: ProductIdsCountData,
        callback: (Result<Long>) -> Unit,
    )

    fun removedProductsIdAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun plusProductsIdCountAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun plusProductIdCountAsyncResult(
        productId: Long,
        quantity: Int,
        callback: (Result<Unit>) -> Unit,
    )

    fun minusProductsIdCountAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun clearAllAsyncResult(
        callback: (Result<Unit>) -> Unit,
    )

}
