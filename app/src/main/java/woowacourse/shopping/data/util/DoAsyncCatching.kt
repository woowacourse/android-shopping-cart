package woowacourse.shopping.data.util

import kotlin.concurrent.thread

inline fun <T> doAsyncCatching(
    crossinline block: () -> T,
    crossinline onResult: (Result<T>) -> Unit,
) {
    thread {
        val result = runCatching { block() }
        onResult(result)
    }
}
