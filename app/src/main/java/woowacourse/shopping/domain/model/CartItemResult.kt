package woowacourse.shopping.domain.model

import woowacourse.shopping.view.cartcounter.ChangeCartItemResultState

data class CartItemResult(
    val cartItemId: Long,
    val counter: CartItemCounter,
) {
    fun increaseCount() {
        counter.increase()
    }

    fun decreaseCount(): ChangeCartItemResultState {
        return counter.decrease()
    }

    fun updateCount(newCount: Int) {
        counter.updateCount(newCount)
    }
}
