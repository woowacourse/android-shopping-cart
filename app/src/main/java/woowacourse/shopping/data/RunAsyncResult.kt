package woowacourse.shopping.data

import kotlin.concurrent.thread

fun <T> runAsyncResult(
    function: () -> T?,
    callback: (Result<T>) -> Unit,
) {
    thread {
        runCatching {
            function()
        }.onSuccess {
            if (it != null) {
                callback(Result.success(it))
            }
        }.onFailure {
            callback(Result.failure(it))
        }
    }
}
