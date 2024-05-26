package woowacourse.shopping.data.source

class DummyProductHistoryDataSource : ProductHistoryDataSource {
    override fun saveProductHistory(productId: Long) {
        if (productHistory.size == MAX_SIZE) {
            productHistory.removeFirst()
        }
        productHistory.add(productId)
    }

    override fun loadAllProductHistory(): List<Long> = productHistory

    override fun deleteAllProductHistory() {
        productHistory.clear()
    }

    companion object {
        private const val MAX_SIZE = 10
        private val productHistory: MutableList<Long> = ArrayDeque(MAX_SIZE)
    }
}
