package woowacourse.shopping.data.source

import woowacourse.shopping.data.model.ProductIdsCountData

interface ShoppingCartProductIdDataSource {
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
