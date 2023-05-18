package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.repository.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private var currentPage: Int = 0,
    private val sizePerPage: Int
) : CartContract.Presenter {
    init {
        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
    }

    override fun removeCartProduct(cartProductModel: CartProductModel) {
        cartRepository.deleteCartProduct(cartProductModel.toDomain())
        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
        view.setResultForChange()
    }

    override fun goToPreviousPage() {
        currentPage--
        updateCartPage()

        if (currentPage == 0) view.updateNavigationVisibility(determineNavigationVisibility())
    }

    override fun goToNextPage() {
        currentPage++
        updateCartPage()
    }

    private fun updateCartPage() {
        val cart = cartRepository.getPage(currentPage, sizePerPage)
        view.updateCart(
            cartProducts = cart.cartProducts.map { it.toView() },
            currentPage = currentPage + 1,
            isLastPage = isLastPageCart(cart)
        )
    }

    private fun isLastPageCart(cart: Cart): Boolean {
        val cartCount = cartRepository.getAllCount()
        return (currentPage * sizePerPage) + cart.cartProducts.size >= cartCount
    }

    private fun determineNavigationVisibility(): Boolean {
        val cartCount = cartRepository.getAllCount()
        return cartCount > sizePerPage || currentPage != 0
    }
}
