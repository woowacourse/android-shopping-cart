package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.presentation.ui.counter.CounterHandler

interface CartEventHandler : CounterHandler {
    fun navigateToShopping()

    fun navigateToDetail(itemId: Long)

    fun deleteCartItem(itemId: Long)

    override fun increaseCount(productId: Long)

    override fun decreaseCount(productId: Long)
}
