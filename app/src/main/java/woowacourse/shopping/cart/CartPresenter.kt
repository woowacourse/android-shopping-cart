package woowacourse.shopping.cart

import woowacourse.shopping.common.data.dao.CartDao
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
    private val countPerPage: Int
) : CartContract.Presenter {
    init {
        updateCartPage()
    }

    override fun removeCartProduct(cartProductModel: CartProductModel) {
        cart = cartState.load().remove(cartProductModel.toDomain())
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
        val cart = cartDao.selectPage(currentPage, countPerPage)
        view.updateCart(cartProductsModel = cart.products.map { it.toView() })
        view.updateNavigator(
            currentPage = currentPage + 1, maxPage = calculateMaxPage() + 1
        )
    }

    private fun calculateMaxPage(): Int = (cartDao.selectAllCount() - 1) / countPerPage
}
