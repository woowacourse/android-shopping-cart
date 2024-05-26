package woowacourse.shopping.data.source

interface ProductHistoryDataSource {
    fun saveProductHistory(productId: Long)

    fun loadAllProductHistory(): List<Long>

    fun deleteAllProductHistory()
}
