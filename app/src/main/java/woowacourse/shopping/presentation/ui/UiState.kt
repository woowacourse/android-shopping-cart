package woowacourse.shopping.presentation.ui

sealed interface UiState<out T : Any> {
    data object None : UiState<Nothing>

    data class Success<out T : Any>(val data: T) : UiState<T>
}
