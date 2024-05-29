package woowacourse.shopping.domain.model

import woowacourse.shopping.view.cartcounter.ChangeCartItemResultState

class CartItemCounter(count: Int = DEFAULT_ITEM_COUNT) {
    var itemCount: Int = count
        private set

    fun increase() {
        itemCount++
    }

    fun decrease(): ChangeCartItemResultState {
        itemCount--
        return if (itemCount > DEFAULT_ITEM_COUNT) {
            ChangeCartItemResultState.Success
        } else {
            ChangeCartItemResultState.Fail
        }
    }

    fun updateCount(count: Int) {
        itemCount = count
    }

    companion object {
        const val DEFAULT_ITEM_COUNT = 0
    }
}
