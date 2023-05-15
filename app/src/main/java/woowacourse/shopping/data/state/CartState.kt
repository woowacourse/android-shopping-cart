package woowacourse.shopping.data.state

import woowacourse.shopping.domain.Cart

object CartState : State<Cart> {
    private var cart: Cart = Cart(emptyList())
    override fun save(t: Cart) {
        cart = t
    }

    override fun load(): Cart {
        return cart
    }
}
