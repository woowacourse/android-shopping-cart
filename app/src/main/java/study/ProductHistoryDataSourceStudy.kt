package study

interface ProductHistoryDataSourceStudy {
    fun saveProductHistory(productId: Long)

    fun loadProductHistory(productId: Long): Long?

    fun loadLatestProduct(): Long

    fun loadAllProductHistory(): List<Long>

    fun deleteAllProductHistory()

    // using callback

    fun saveProductHistoryAsync(
        productId: Long,
        callback: (Boolean) -> Unit,
    )

    fun loadProductHistoryAsync(
        productId: Long,
        callback: (Long) -> Unit,
    )

    fun loadLatestProductAsync(callback: (Long) -> Unit)

    fun loadAllProductHistoryAsync(callback: (List<Long>) -> Unit)

    // return Result

    fun saveProductHistoryResult(productId: Long): Result<Unit>

    fun loadProductHistoryResult(productId: Long): Result<Long>

    fun loadLatestProductResult(): Result<Long>

    fun loadAllProductHistoryResult(): Result<List<Long>>


    // using callback with Result

    fun saveProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    )

    fun loadProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Long>) -> Unit,
    )

    fun loadLatestProductAsyncResult(callback: (Result<Long>) -> Unit)

    fun loadAllProductHistoryAsyncResult(callback: (Result<List<Long>>) -> Unit)

}
