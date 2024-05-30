package woowacourse.shopping.data.source

interface ProductHistoryDataSource {
    fun saveProductHistory(productId: Long)

    fun loadProductHistory(productId: Long): Long?

    fun loadLatestProduct(): Long

    fun loadAllProductHistory(): List<Long>

    fun deleteAllProductHistory()

    fun saveProductHistoryAsync(productId: Long, callback: (Boolean) -> Unit)

    fun loadProductHistoryAsync(productId: Long, callback: (Long) -> Unit)

    fun loadLatestProductAsync(callback: (Long) -> Unit)

    fun loadAllProductHistoryAsync(callback: (List<Long>) -> Unit)

}
