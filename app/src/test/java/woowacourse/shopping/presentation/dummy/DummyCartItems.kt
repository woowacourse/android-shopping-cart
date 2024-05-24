package woowacourse.shopping.presentation.dummy

import woowacourse.shopping.db.cart.Cart

class DummyCartItems {
    val carts =
        mutableListOf(
            Cart(1, 1, 1),
            Cart(2, 1, 2),
            Cart(3, 1, 3),
            Cart(4, 1, 4),
            Cart(5, 1, 5),
            Cart(6, 1, 6),
        )
}
