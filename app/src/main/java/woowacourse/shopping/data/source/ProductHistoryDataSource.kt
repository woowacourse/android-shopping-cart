package woowacourse.shopping.data.source

interface ProductHistoryDataSource {
    fun saveProductHistory(productId: Long)

    fun loadProductHistory(productId: Long): Long?

    fun loadAllProductHistory(): List<Long>

    fun deleteAllProductHistory()
}
