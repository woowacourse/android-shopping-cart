package woowacourse.shopping.data.util

import kotlin.concurrent.thread

inline fun <T> runCatchingInThread(
    crossinline block: () -> T,
    crossinline callback: (Result<T>) -> Unit,
) {
    thread {
        val result = runCatching { block() }
        callback(result)
    }
}
