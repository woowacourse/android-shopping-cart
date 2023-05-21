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
    private var cartTotalPrice: Int = 0
    private var cartTotalAmount: Int = 0

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
        applyCartProductCheckedChange(cartProductModel.toDomain(), isChecked)
    }

    override fun updateAllChecked() {
        val isAllChecked = cartRepository.isAllCheckedInPage(currentPage, sizePerPage)
        view.updateAllChecked(isAllChecked)
    }

    override fun decreaseCartProductAmount(cartProductModel: CartProductModel) {
        if (cartProductModel.amount > 1) {
            val prevCartProduct = cartProductModel.toDomain()
            val newCartProduct = prevCartProduct.decreaseAmount()
            updateCartProduct(prevCartProduct, newCartProduct)

            if (cartProductModel.isChecked) {
                subtractProductPriceToCartTotalPrice(cartProductModel)
                decreaseTotalAmount()
            }
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

    override fun updateCartProductCheckedInPage(isChecked: Boolean) {
        val cart = cartRepository.getPage(currentPage, sizePerPage)
        cart.cartProducts.forEach {
            if (it.isChecked != isChecked) {
                applyCartProductCheckedChange(it, isChecked)
            }
        }
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
        cartTotalPrice = cartRepository.getTotalPrice()
        view.updateCartTotalPrice(cartTotalPrice)
    }

    private fun setupTotalAmount() {
        cartTotalAmount = cartRepository.getTotalAmount()
        view.updateCartTotalAmount(cartTotalAmount)
    }

    private fun isLastPageCart(cart: Cart): Boolean {
        val cartCount = cartRepository.getAllCount()
        return (currentPage * sizePerPage) + cart.cartProducts.size >= cartCount
    }

    private fun determineNavigationVisibility(): Boolean {
        val cartCount = cartRepository.getAllCount()
        return cartCount > sizePerPage || currentPage != 0
    }

    private fun applyCartProductCheckedChange(cartProduct: CartProduct, isChecked: Boolean) {
        val newCartProduct = cartProduct.changeChecked(isChecked)
        cartRepository.replaceCartProduct(cartProduct, newCartProduct)
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
        cartRepository.replaceCartProduct(prev, new)
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
