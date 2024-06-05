package woowacourse.shopping.data.source

interface ProductHistoryDataSource {
    fun saveProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun loadLatestProductAsyncResult(callback: (Result<Long>) -> Unit)

    fun loadAllProductHistoryAsyncResult(callback: (Result<List<Long>>) -> Unit)
}
