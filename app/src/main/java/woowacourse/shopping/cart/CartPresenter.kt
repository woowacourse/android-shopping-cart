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
    private var currentPage: Page = Page(0),
    private val sizePerPage: Int,
    private var initialCart: Cart = Cart(emptyList()),
    private var cart: Cart = Cart(emptyList())
) : CartContract.Presenter {
    private var cartTotalPrice: Int = 0
    private var cartTotalAmount: Int = 0

    init {
        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
        setupTotalPrice()
        setupTotalAmount()
    }

    override fun removeCartProduct(cartProductModel: CartProductModel) {
        val cartProduct = cartProductModel.toDomain()
        cartRepository.deleteCartProduct(cartProduct)
        cart = cart.removeCartProduct(cartProduct)

        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
    }

    override fun goToPreviousPage() {
        if (currentPage.isFirstPage()) return

        currentPage = currentPage.moveToPreviousPage()
        updateCartPage()
        if (currentPage.isFirstPage()) view.updateNavigationVisibility(determineNavigationVisibility())
    }

    override fun goToNextPage() {
        currentPage = currentPage.moveToNextPage()
        updateCartPage()
    }

    override fun reverseCartProductChecked(cartProductModel: CartProductModel) {
        val isChecked = !cartProductModel.isChecked
        applyCartProductCheckedChange(cartProductModel.toDomain(), isChecked)
    }

    override fun updateAllChecked() {
        val cartInPage = getCartInPage()
        view.updateAllChecked(cartInPage.isAllChecked())
    }

    override fun decreaseCartProductAmount(cartProductModel: CartProductModel) {
        if (cartProductModel.amount <= 1) return

        val prevCartProduct = cartProductModel.toDomain()
        val newCartProduct = prevCartProduct.decreaseAmount()
        updateCartProduct(prevCartProduct, newCartProduct)

        if (cartProductModel.isChecked) {
            subtractProductPriceToCartTotalPrice(cartProductModel)
            decreaseTotalAmount()
        }
    }

    override fun increaseCartProductAmount(cartProductModel: CartProductModel) {
        val prevCartProduct = cartProductModel.toDomain()
        val newCartProduct = prevCartProduct.increaseAmount()
        updateCartProduct(prevCartProduct, newCartProduct)

        if (cartProductModel.isChecked) {
            addProductPriceToCartTotalPrice(cartProductModel)
            increaseTotalAmount()
        }
    }

    override fun changeAllChecked(isChecked: Boolean) {
        val cart = getCartInPage()
        cart.cartProducts.forEach {
            if (it.isChecked != isChecked) {
                applyCartProductCheckedChange(it, isChecked)
            }
        }
    }

    override fun checkCartChanged() {
        val cartProducts = cart.cartProducts.map { it.copy(isChecked = true) }
        if (cartProducts != initialCart.cartProducts) {
            view.notifyCartChanged()
        }
    }

    private fun updateCartPage() {
        val newCart = getCartInPage()
        view.updateCart(
            cartProducts = newCart.cartProducts.map { it.toView() },
            currentPage = currentPage.value + 1,
            isLastPage = isLastPageCart(newCart)
        )
        updateAllChecked()
    }

    private fun getCartInPage(): Cart {
        val startIndex = currentPage.value * sizePerPage
        return if (startIndex < cart.cartProducts.size) {
            cart.getSubCart(startIndex, startIndex + sizePerPage)
        } else {
            cartRepository.getPage(currentPage.value, sizePerPage).apply {
                initialCart = Cart(initialCart.cartProducts + cartProducts)
                cart = Cart(cart.cartProducts + cartProducts)
            }
        }
    }

    private fun setupTotalPrice() {
        cartTotalPrice = cartRepository.getTotalPrice()
        view.updateCartTotalPrice(cartTotalPrice)
    }

    private fun setupTotalAmount() {
        cartTotalAmount = cartRepository.getTotalAmount()
        view.updateCartTotalAmount(cartTotalAmount)
    }

    private fun isLastPageCart(cart: Cart): Boolean {
        val cartCount = cartRepository.getAllCount()
        return (currentPage.value * sizePerPage) + cart.cartProducts.size >= cartCount
    }

    private fun determineNavigationVisibility(): Boolean {
        val cartCount = cartRepository.getAllCount()
        return cartCount > sizePerPage || !currentPage.isFirstPage()
    }

    private fun applyCartProductCheckedChange(cartProduct: CartProduct, isChecked: Boolean) {
        val newCartProduct = cartProduct.changeChecked(isChecked)
        cart = cart.replaceCartProduct(cartProduct, newCartProduct)
        view.updateCartProduct(cartProduct.toView(), newCartProduct.toView())

        applyProductTotalPriceToCartTotalPrice(newCartProduct)
        applyProductAmountToCartTotalAmount(newCartProduct)
    }

    private fun applyProductTotalPriceToCartTotalPrice(cartProduct: CartProduct) {
        val productTotalPrice = cartProduct.product.price * cartProduct.amount
        if (cartProduct.isChecked) {
            cartTotalPrice += productTotalPrice
            view.updateCartTotalPrice(cartTotalPrice)
        } else {
            cartTotalPrice -= productTotalPrice
            view.updateCartTotalPrice(cartTotalPrice)
        }
    }

    private fun applyProductAmountToCartTotalAmount(cartProduct: CartProduct) {
        if (cartProduct.isChecked) {
            cartTotalAmount += cartProduct.amount
            view.updateCartTotalAmount(cartTotalAmount)
        } else {
            cartTotalAmount -= cartProduct.amount
            view.updateCartTotalAmount(cartTotalAmount)
        }
    }

    private fun updateCartProduct(prev: CartProduct, new: CartProduct) {
        cartRepository.modifyCartProduct(new)
        cart = cart.replaceCartProduct(prev, new)
        view.updateCartProduct(prev.toView(), new.toView())
    }

    private fun subtractProductPriceToCartTotalPrice(cartProductModel: CartProductModel) {
        cartTotalPrice -= cartProductModel.product.price
        view.updateCartTotalPrice(cartTotalPrice)
    }

    private fun decreaseTotalAmount() {
        cartTotalAmount -= 1
        view.updateCartTotalAmount(cartTotalAmount)
    }

    private fun addProductPriceToCartTotalPrice(cartProductModel: CartProductModel) {
        cartTotalPrice += cartProductModel.product.price
        view.updateCartTotalPrice(cartTotalPrice)
    }

    private fun increaseTotalAmount() {
        cartTotalAmount += 1
        view.updateCartTotalAmount(cartTotalAmount)
    }
}
