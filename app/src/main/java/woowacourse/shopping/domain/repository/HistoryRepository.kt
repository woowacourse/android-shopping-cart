package woowacourse.shopping.domain.repository

interface HistoryRepository {
    fun getHistory(onResult: (List<Long>) -> Unit)

    fun saveHistory(
        productId: Long,
        onResult: () -> Unit,
    )
}
