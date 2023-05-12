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
        updateCartPage()
    }

    override fun removeCartProduct(cartProductModel: CartProductModel) {
        cart = cartState.load().remove(cartProductModel.toDomain())
        cartState.save(cart)
        cartDao.deleteCartProductByOrdinal(cartProductModel.ordinal)
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
        cartState.save(cartDao.selectAll())
        val cart = cartDao.selectPage(currentPage, sizePerPage)
        view.updateCart(cartProductsModel = cart.cartProducts.map { it.toView() })
        view.updateNavigator(
            currentPage = currentPage + 1, maxPage = calculateMaxPage() + 1
        )
    }

    private fun calculateMaxPage(): Int = (cartDao.selectAllCount() - 1) / sizePerPage
}
