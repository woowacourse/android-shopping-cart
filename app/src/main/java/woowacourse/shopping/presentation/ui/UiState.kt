package woowacourse.shopping.presentation.ui

sealed interface UiState<out T : Any> {
    data object None : UiState<Nothing>

    data class Finish<out T : Any>(val data: T) : UiState<T>

    data class Error(val msg: String) : UiState<Nothing>
}
