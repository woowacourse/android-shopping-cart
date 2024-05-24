package woowacourse.shopping.presentation.ui.counter

interface CartCounterHandler {
    fun increaseCount(itemId: Long)

    fun decreaseCount(itemId: Long)
}
