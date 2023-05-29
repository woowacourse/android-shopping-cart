package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toDomain
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.ShoppingProduct
import woowacourse.shopping.domain.repository.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private var currentPage: Page = Page(0),
    private val sizePerPage: Int,
    private var initialCart: Cart = Cart(emptyList()),
    private var cart: Cart = Cart(emptyList())
) : CartContract.Presenter {
    private var totalCount: Int = 0
    private var selectedCartTotalPrice: Int = 0
    private var selectedCartTotalAmount: Int = 0

    init {
        setupTotalCount()
        setupTotalPrice()
        setupTotalAmount()
        view.updateNavigationVisibility(determineNavigationVisibility())
        updateCartPage()
    }

    override fun removeCartProduct(cartProductModel: CartProductModel) {
        val cartProduct = cartProductModel.toDomain()
        cartRepository.deleteCartProduct(cartProduct)
        cart = cart.removeCartProduct(cartProduct)
        totalCount--

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

    override fun checkProductsChanged() {
        val initialShoppingProducts =
            initialCart.cartProducts.map { ShoppingProduct(it.product, it.amount) }
        val shoppingProducts =
            cart.cartProducts.map { ShoppingProduct(it.product, it.amount) }
        if (initialShoppingProducts != shoppingProducts) {
            view.notifyProductsChanged()
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

    private fun setupTotalCount() {
        totalCount = cartRepository.getAllCount()
    }

    private fun setupTotalPrice() {
        selectedCartTotalPrice = cartRepository.getTotalPrice()
        view.updateCartTotalPrice(selectedCartTotalPrice)
    }

    private fun setupTotalAmount() {
        selectedCartTotalAmount = cartRepository.getTotalAmount()
        view.updateCartTotalAmount(selectedCartTotalAmount)
    }

    private fun isLastPageCart(cart: Cart): Boolean {
        return (currentPage.value * sizePerPage) + cart.cartProducts.size >= totalCount
    }

    private fun determineNavigationVisibility(): Boolean {
        return totalCount > sizePerPage || !currentPage.isFirstPage()
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
            selectedCartTotalPrice += productTotalPrice
            view.updateCartTotalPrice(selectedCartTotalPrice)
        } else {
            selectedCartTotalPrice -= productTotalPrice
            view.updateCartTotalPrice(selectedCartTotalPrice)
        }
    }

    private fun applyProductAmountToCartTotalAmount(cartProduct: CartProduct) {
        if (cartProduct.isChecked) {
            selectedCartTotalAmount += cartProduct.amount
            view.updateCartTotalAmount(selectedCartTotalAmount)
        } else {
            selectedCartTotalAmount -= cartProduct.amount
            view.updateCartTotalAmount(selectedCartTotalAmount)
        }
    }

    private fun updateCartProduct(prev: CartProduct, new: CartProduct) {
        cartRepository.modifyCartProduct(new)
        cart = cart.replaceCartProduct(prev, new)
        view.updateCartProduct(prev.toView(), new.toView())
    }

    private fun subtractProductPriceToCartTotalPrice(cartProductModel: CartProductModel) {
        selectedCartTotalPrice -= cartProductModel.product.price
        view.updateCartTotalPrice(selectedCartTotalPrice)
    }

    private fun decreaseTotalAmount() {
        selectedCartTotalAmount -= 1
        view.updateCartTotalAmount(selectedCartTotalAmount)
    }

    private fun addProductPriceToCartTotalPrice(cartProductModel: CartProductModel) {
        selectedCartTotalPrice += cartProductModel.product.price
        view.updateCartTotalPrice(selectedCartTotalPrice)
    }

    private fun increaseTotalAmount() {
        selectedCartTotalAmount += 1
        view.updateCartTotalAmount(selectedCartTotalAmount)
    }
}
