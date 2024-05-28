package woowacourse.shopping.presentation.ui.counter

interface CounterHandler {
    fun increaseCount(productId: Long)

    fun decreaseCount(productId: Long)
}
