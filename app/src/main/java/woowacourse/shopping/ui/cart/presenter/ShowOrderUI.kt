package woowacourse.shopping.ui.cart.presenter

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.ui.cart.OrderView

class ShowOrderUI(private val view: OrderView) {
    operator fun invoke(selectedCartItems: Set<CartItem>) {
        view.setOrderPrice(selectedCartItems.sumOf(CartItem::getOrderPrice))
        view.setOrderCount(selectedCartItems.size)
    }
}