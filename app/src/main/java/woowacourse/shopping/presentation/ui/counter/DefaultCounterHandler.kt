package woowacourse.shopping.presentation.ui.counter

interface DefaultCounterHandler {
    fun increaseCount(productId: Long)

    fun decreaseCount(productId: Long)
}
