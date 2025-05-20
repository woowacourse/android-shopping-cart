package woowacourse.shopping.view.main.vm.state

sealed interface LoadState {
    object CanLoad : LoadState

    object CannotLoad : LoadState

    companion object {
        fun of(value: Boolean) = if (value) CanLoad else CannotLoad
    }
}
