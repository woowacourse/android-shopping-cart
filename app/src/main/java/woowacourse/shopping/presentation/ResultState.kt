package woowacourse.shopping.presentation

sealed class ResultState<out T> {
    data class Success<out T>(
        val data: T,
    ) : ResultState<T>()

    data class Failure(
        val throwable: Throwable? = null,
    ) : ResultState<Nothing>()
}
