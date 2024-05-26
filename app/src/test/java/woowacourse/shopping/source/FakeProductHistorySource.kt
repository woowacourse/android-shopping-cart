package woowacourse.shopping.source

import woowacourse.shopping.data.source.ProductHistoryDataSource

class FakeProductHistorySource(
    private val history: MutableList<Long> = ArrayDeque(MAX_SIZE),
) : ProductHistoryDataSource {
    override fun saveProductHistory(productId: Long) {
        if (history.contains(productId)) {
            history.remove(productId)
        }

        if (history.size == MAX_SIZE) {
            history.removeFirst()
        }

        history.add(productId)
    }

    override fun loadProductHistory(productId: Long): Long? = history.find { it == productId }

    override fun loadLatestProduct(): Long = history.last()

    override fun loadAllProductHistory(): List<Long> {
        return history
    }

    override fun deleteAllProductHistory() {
        history.clear()
    }

    companion object {
        private const val MAX_SIZE = 10
    }
}
