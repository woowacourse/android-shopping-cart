package study.repository

import woowacourse.shopping.domain.model.Product

interface ProductHistoryRepositoryStudy {
    fun saveProductHistory(productId: Long)

    fun loadAllProductHistory(): List<Product>

    fun loadProductHistory(productId: Long): Product

    fun loadLatestProduct(): Product

    // async

    fun saveProductHistoryAsync(
        productId: Long,
        callback: (Boolean) -> Unit,
    )

    fun loadAllProductHistoryAsync(callback: (List<Product>) -> Unit)

    fun loadProductHistoryAsync(
        productId: Long,
        callback: (Product) -> Unit,
    )

    fun loadLatestProductAsync(callback: (Long) -> Unit)

    // async result
    fun saveProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit
    )

    fun loadAllProductHistoryAsyncResult(callback: (Result<List<Product>>) -> Unit)

    fun loadProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Product>) -> Unit
    )

    fun loadLatestProductIdAsyncResult(callback: (Result<Long>) -> Unit)

}
