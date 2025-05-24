package woowacourse.shopping.data

import kotlin.concurrent.thread

inline fun <T> runThread(
    crossinline block: () -> T,
    crossinline onResult: (Result<T>) -> Unit,
) {
    thread {
        onResult(runCatching { block() })
    }
}
