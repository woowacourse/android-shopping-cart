package woowacourse.shopping.feature.model

sealed class State {
    object Success : State()

    object Failure : State()
}
