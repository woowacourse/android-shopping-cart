package woowacourse.shopping.ui.state

sealed interface UiState<out T> {
    data class SUCCESS<T>(val data: T) : UiState<T>

    data class ERROR(val error: Throwable) : UiState<Nothing>

    data object LOADING : UiState<Nothing>
}
