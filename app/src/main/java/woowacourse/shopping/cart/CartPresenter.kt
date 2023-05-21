package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.repository.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private var currentPage: Int = 0,
    private val sizePerPage: Int
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

    override fun changeCartProductChecked(cartProductModel: CartProductModel) {
        val isChecked = !cartProductModel.isChecked
        val newCartProduct = cartProductModel.toDomain().changeChecked(isChecked)
        cartRepository.replaceCartProduct(cartProductModel.toDomain(), newCartProduct)
        view.updateCartProduct(cartProductModel, newCartProduct.toView())

        applyPriceToTotalPrice(newCartProduct)
        applyAmountToTotalAmount(newCartProduct)
    }

    override fun updateAllChecked() {
        val isAllChecked = cartRepository.isAllCheckedInPage(currentPage, sizePerPage)
        view.updateAllChecked(isAllChecked)
    }

    private fun updateCartPage() {
        val newCart = getCartInPage()
        view.updateCart(
            cartProducts = newCart.cartProducts.map { it.toView() },
            currentPage = currentPage + 1,
            isLastPage = isLastPageCart(newCart)
        )
        updateAllChecked()
    }

    private fun getCartInPage(): Cart {
        return cartRepository.getPage(currentPage, sizePerPage)
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

    private fun applyPriceToTotalPrice(cartProduct: CartProduct) {
        if (cartProduct.isChecked) {
            totalPrice += cartProduct.product.price * cartProduct.amount
            view.updateCartTotalPrice(totalPrice)
        } else {
            totalPrice -= cartProduct.product.price * cartProduct.amount
            view.updateCartTotalPrice(totalPrice)
        }
    }

    private fun applyAmountToTotalAmount(cartProduct: CartProduct) {
        if (cartProduct.isChecked) {
            totalAmount += cartProduct.amount
            view.updateCartTotalAmount(totalAmount)
        } else {
            totalAmount -= cartProduct.amount
            view.updateCartTotalAmount(totalAmount)
        }
    }
}
