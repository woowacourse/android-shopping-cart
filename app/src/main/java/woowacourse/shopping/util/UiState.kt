package woowacourse.shopping.util

interface UiState {
    interface Loading : UiState

    interface Complete<T : Any?> : UiState {
        val result: T
    }

    interface Error : UiState {
        val throwable: Throwable?
    }
}
