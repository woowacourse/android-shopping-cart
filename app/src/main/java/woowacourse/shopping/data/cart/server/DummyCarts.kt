package woowacourse.shopping.data.cart.server

import woowacourse.shopping.domain.model.Cart
import woowacourse.shopping.domain.model.Carts
import woowacourse.shopping.domain.model.Goods.Companion.dummyGoods

object DummyCarts {
    private val dummyList =
        mutableListOf<Cart>().apply {
            dummyGoods.forEach { goods ->
                add(Cart(goods, quantity = 1))
            }
        }

    fun getCarts(): Carts =
        Carts(
            carts = dummyList.toList(),
            totalQuantity = dummyList.sumOf { it.quantity },
        )

    fun insert(cart: Cart) {
        dummyList.removeAll { it.goods.id == cart.goods.id }
        dummyList.add(cart)
    }

    fun delete(cart: Cart) {
        dummyList.removeIf { it.goods.id == cart.goods.id }
    }
}
