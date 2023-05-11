package woowacourse.shopping.cart

import woowacourse.shopping.common.data.database.dao.CartDao
import woowacourse.shopping.common.data.database.state.CartState
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.domain.Cart

class CartPresenter(
    private val view: CartContract.View,
    private var cart: Cart = Cart(emptyList()),
    private val cartState: State<Cart> = CartState,
    private val cartDao: CartDao,
    private var currentPage: Int = 0,
    private val sizePerPage: Int
) : CartContract.Presenter {
    init {
        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
    }

    override fun removeCartProduct(cartProductModel: CartProductModel) {
        cart = cart.remove(cartProductModel.toDomain())
        cartState.save(cart)
        cartDao.deleteCartProductByOrdinal(cartProductModel.ordinal)
        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
    }

    override fun goToPreviousPage() {
        currentPage--
        updateCartPage()
    }

    override fun goToNextPage() {
        currentPage++
        updateCartPage()
    }

    private fun updateCartPage() {
        cart = cartDao.selectPage(currentPage, sizePerPage)
        view.updateCart(cart.cartProducts.map { it.toView() }, currentPage)
    }

    private fun determineNavigationVisibility(): Boolean {
        val cartCount = cartDao.selectAllCount()
        return cartCount > sizePerPage
    }
}
