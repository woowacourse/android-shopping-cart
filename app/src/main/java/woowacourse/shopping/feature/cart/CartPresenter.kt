package woowacourse.shopping.feature.cart

import com.example.domain.Cart
import com.example.domain.CartProduct
import com.example.domain.PaymentCalculator
import com.example.domain.repository.CartRepository
import woowacourse.shopping.model.CartProductState
import woowacourse.shopping.model.mapper.toUi

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private val cart: Cart
) : CartContract.Presenter {

    private val maxProductsPerPage: Int = 5
    private val minPageNumber: Int = 1
    private val maxPageNumber: Int
        get() = getMaxPageNumber(cart.products.size)

    private var pageNumber: Int = 1

    override fun loadCart() {
        val fromIndex = pageNumber * maxProductsPerPage - maxProductsPerPage
        val toIndex = pageNumber * maxProductsPerPage - 1

        cart.updateAll(cartRepository.getAll())

        view.setCartPageNumber(pageNumber)
        view.setCartProducts(cart.subList(fromIndex, toIndex).map(CartProduct::toUi))
        view.hidePageSelectorView()
        if (minPageNumber < maxPageNumber) view.showPageSelectorView()
    }

    override fun loadCheckedCartProductCount() {
        view.setCartProductCount(cart.getCheckedItemCount())
    }

    override fun plusPageNumber() {
        val pageNumber = (++pageNumber).coerceAtMost(maxPageNumber)

        view.setCartPageNumberMinusEnable(true)
        if (pageNumber < maxPageNumber) view.setCartPageNumberPlusEnable(true)
        if (maxPageNumber <= pageNumber) view.setCartPageNumberPlusEnable(false)
        loadCart()
    }

    override fun minusPageNumber() {
        val pageNumber = (--pageNumber).coerceAtLeast(minPageNumber)

        view.setCartPageNumberPlusEnable(true)
        if (minPageNumber < pageNumber) view.setCartPageNumberMinusEnable(true)
        if (pageNumber <= minPageNumber) view.setCartPageNumberMinusEnable(false)
        loadCart()
    }

    override fun updateCount(productId: Int, count: Int) {
        cartRepository.updateCartProductCount(productId, count)
        cart.updateCountByProductId(productId, count)
        view.setTotalCost(PaymentCalculator.totalPaymentAmount(cart.products).toInt())
    }

    override fun updateChecked(productId: Int, checked: Boolean) {
        cartRepository.updateCartProductChecked(productId, checked)
        cart.updateCheckedByProductId(productId, checked)
        view.setTotalCost(PaymentCalculator.totalPaymentAmount(cart.products).toInt())
    }

    override fun deleteCartProduct(cartProductState: CartProductState) {
        cartRepository.deleteCartProduct(cartProductState.productId)
        cart.removeByProductId(cartProductState.productId)
        loadCart()
    }

    override fun checkAll() {
        when (cart.isAllChecked()) {
            true -> {
                cart.setAllChecked(false)
                cart.products.forEach {
                    cartRepository.updateCartProductChecked(it.productId, false)
                }
            }
            false -> {
                cart.setAllChecked(true)
                cart.products.forEach {
                    cartRepository.updateCartProductChecked(it.productId, true)
                }
            }
        }
        view.setTotalCost(PaymentCalculator.totalPaymentAmount(cart.products).toInt())
    }

    private fun getMaxPageNumber(cartsSize: Int): Int {
        if (cartsSize == 0) return 1
        return (cartsSize - 1) / maxProductsPerPage + 1
    }
}
