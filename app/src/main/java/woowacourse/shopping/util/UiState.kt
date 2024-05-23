package woowacourse.shopping.util

interface UiState {
    interface Loading : UiState

    interface CompleteWithResult<T : Any?> : UiState {
        val result: T
    }

    interface Complete : UiState

    interface Fail

    interface Error : UiState {
        val throwable: Throwable?
    }
}
