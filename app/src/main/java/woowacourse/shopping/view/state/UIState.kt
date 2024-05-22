package woowacourse.shopping.view.state

sealed class UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>()

    data object Empty : UIState<Nothing>()

    data class Error(val exception: Throwable) : UIState<Nothing>()
}
