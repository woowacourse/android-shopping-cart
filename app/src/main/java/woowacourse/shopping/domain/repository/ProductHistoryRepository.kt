package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface ProductHistoryRepository {
    fun saveProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun loadAllProductHistoryAsyncResult(callback: (Result<List<Product>>) -> Unit)

    fun loadLatestProductIdAsyncResult(callback: (Result<Long>) -> Unit)
}
