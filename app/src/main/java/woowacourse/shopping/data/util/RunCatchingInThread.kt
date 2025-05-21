package woowacourse.shopping.data.util

import kotlin.concurrent.thread

inline fun <T> runCatchingInThread(
    crossinline onResult: (Result<T>) -> Unit,
    crossinline block: () -> T,
) {
    thread {
        val result = runCatching { block() }
        onResult(result)
    }
}
