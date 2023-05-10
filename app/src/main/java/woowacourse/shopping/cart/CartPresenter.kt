package woowacourse.shopping.cart

import woowacourse.shopping.common.data.database.dao.CartDao
import woowacourse.shopping.common.data.database.state.CartState
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct

class CartPresenter(
    private val view: CartContract.View,
    private var cart: Cart = Cart(emptyList()),
    private val cartState: State<Cart> = CartState,
    private val cartDao: CartDao
) : CartContract.Presenter {
    init {
        // cart = cartDao.select
        view.updateCart(cart.cartProducts.map { it.toView() })
    }

    override fun resumeView() {
        cart = cartState.load()
        view.updateCart(cart.cartProducts.map { it.toView() })
    }

    override fun removeCartProduct(cartProduct: CartProduct) {
        cart = cart.remove(cartProduct)
        cartDao.deleteCartProductByOrdinal(cartProduct.ordinal)
        view.updateCart(cart.cartProducts.map { it.toView() })
    }
}
