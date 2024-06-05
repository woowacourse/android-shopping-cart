package woowacourse.shopping.source

import woowacourse.shopping.data.source.ProductHistoryDataSource
import kotlin.concurrent.thread

class FakeProductHistorySource(
    private val history: MutableList<Long> = ArrayDeque(MAX_SIZE),
) : ProductHistoryDataSource {
    override fun saveProductHistoryAsyncResult(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        thread {
            callback(
                runCatching {
                    if (history.contains(productId)) {
                        history.remove(productId)
                    }

                    if (history.size == MAX_SIZE) {
                        history.removeFirst()
                    }

                    history.add(productId)
                    Unit
                },
            )
        }
    }

    override fun loadLatestProductAsyncResult(callback: (Result<Long>) -> Unit) {
        thread {
            callback(
                runCatching {
                    history.last()
                },
            )
        }
    }

    override fun loadAllProductHistoryAsyncResult(callback: (Result<List<Long>>) -> Unit) {
        thread {
            callback(
                runCatching {
                    history
                },
            )
        }
    }

    companion object {
        private const val MAX_SIZE = 10
    }
}
