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
    private val sizePerPage: Int,
    private var cart: Cart = Cart(emptyList())
) : CartContract.Presenter {
    private var totalPrice: Int = 0
    private var totalAmount: Int = 0

    init {
        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
        setupTotalPrice()
        setupTotalAmount()
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
        val newCart = getCartInPage()
        view.updateCart(
            cartProducts = newCart.cartProducts.map { it.toView() },
            currentPage = currentPage + 1,
            isLastPage = isLastPageCart(newCart)
        )
    }

    private fun getCartInPage(): Cart {
        val startIndex = currentPage * sizePerPage
        val newCart = if (startIndex < cart.cartProducts.size) {
            cart.getSubCart(startIndex, startIndex + sizePerPage)
        } else {
            cartRepository.getPage(currentPage, sizePerPage).apply {
                cart = Cart(cart.cartProducts + cartProducts)
            }
        }
        return newCart
    }

    private fun setupTotalPrice() {
        totalPrice = cartRepository.getTotalPrice()
        view.updateCartTotalPrice(totalPrice)
    }

    private fun setupTotalAmount() {
        totalAmount = cartRepository.getTotalAmount()
        view.updateCartTotalAmount(totalAmount)
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
