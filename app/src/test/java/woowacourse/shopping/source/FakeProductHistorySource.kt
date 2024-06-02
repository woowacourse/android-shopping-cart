package woowacourse.shopping.source

import woowacourse.shopping.data.source.ProductHistoryDataSource
import kotlin.concurrent.thread

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

    override fun saveProductHistoryAsync(
        productId: Long,
        callback: (Boolean) -> Unit,
    ) {
        thread {
            saveProductHistory(productId)
            callback(true)
        }
    }

    override fun loadProductHistoryAsync(
        productId: Long,
        callback: (Long) -> Unit,
    ) {
        thread {
            callback(loadProductHistory(productId) ?: -1)
        }
    }

    override fun loadLatestProductAsync(callback: (Long) -> Unit) {
        thread {
            callback(history.last())
        }
    }

    override fun loadAllProductHistoryAsync(callback: (List<Long>) -> Unit) {
        thread {
            callback(history)
        }
    }

    override fun saveProductHistoryResult(productId: Long): Result<Unit> {
        return runCatching {
            saveProductHistory(productId)
        }
    }

    override fun loadProductHistoryResult(productId: Long): Result<Long> {
        return runCatching {
            history.find { it == productId } ?: throw NoSuchElementException()
        }
    }

    override fun loadLatestProductResult(): Result<Long> {
        return runCatching {
            history.last()
        }
    }

    override fun loadAllProductHistoryResult(): Result<List<Long>> {
        return runCatching {
            history
        }
    }

    override fun saveProductHistoryAsyncResult(productId: Long, callback: (Result<Unit>) -> Unit) {
        thread {
            callback(runCatching {
                saveProductHistory(productId)
            })
        }
    }

    override fun loadProductHistoryAsyncResult(productId: Long, callback: (Result<Long>) -> Unit) {
        thread {
            callback(runCatching {
                history.find { it == productId } ?: throw NoSuchElementException()
            })
        }
    }

    override fun loadLatestProductAsyncResult(callback: (Result<Long>) -> Unit) {
        thread {
            callback(runCatching {
                history.last()
            })
        }
    }

    override fun loadAllProductHistoryAsyncResult(callback: (Result<List<Long>>) -> Unit) {
        thread {
            callback(runCatching {
                history
            })
        }
    }

    companion object {
        private const val MAX_SIZE = 10
    }
}
