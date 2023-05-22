package woowacourse.shopping.feature.cart

import com.example.domain.CartProduct
import com.example.domain.PaymentCalculator
import com.example.domain.repository.CartRepository
import woowacourse.shopping.model.CartProductState
import woowacourse.shopping.model.mapper.toUi

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository
) : CartContract.Presenter {

    private val maxProductsPerPage: Int = 5
    private val minPageNumber: Int = 1
    private val maxPageNumber: Int
        get() = getMaxPageNumber(cartRepository.getAll().size)

    private var pageNumber: Int = 1

    override fun loadCart() {
        val startIndex = pageNumber * maxProductsPerPage - maxProductsPerPage
        val endIndex = pageNumber * maxProductsPerPage - 1

        val cartProducts: List<CartProductState> = cartRepository.getAll().map(CartProduct::toUi)
        val items: List<CartProductState> =
            cartProducts.filterIndexed { index, _ -> index in startIndex..endIndex }

        view.setCartPageNumber(pageNumber)
        view.setCartProducts(items)
        view.hidePageSelectorView()
        if (minPageNumber < maxPageNumber) view.showPageSelectorView()
    }

    override fun loadCheckedCartProductCount() {
        val cartProductCount = cartRepository.getAll().filter { it.checked }.size
        view.setCartProductCount(cartProductCount)
    }

    override fun plusPageNumber() {
        (++pageNumber).coerceAtMost(maxPageNumber)

        view.setCartPageNumberMinusEnable(true)
        if (pageNumber > maxPageNumber) return
        if (pageNumber < maxPageNumber) view.setCartPageNumberPlusEnable(true)
        if (pageNumber == maxPageNumber) view.setCartPageNumberPlusEnable(false)
        loadCart()
    }

    override fun minusPageNumber() {
        (--pageNumber).coerceAtLeast(minPageNumber)

        view.setCartPageNumberPlusEnable(true)
        if (pageNumber < minPageNumber) return
        if (pageNumber > minPageNumber) view.setCartPageNumberMinusEnable(true)
        if (pageNumber == minPageNumber) view.setCartPageNumberMinusEnable(false)
        loadCart()
    }

    override fun updateCount(productId: Int, count: Int) {
        cartRepository.updateCartProductCount(productId, count)
        view.setTotalCost(PaymentCalculator.totalPaymentAmount(cartRepository.getAll()).toInt())
    }

    override fun updateChecked(productId: Int, checked: Boolean) {
        cartRepository.updateCartProductChecked(productId, checked)
        view.setTotalCost(PaymentCalculator.totalPaymentAmount(cartRepository.getAll()).toInt())
    }

    override fun deleteCartProduct(cartProductState: CartProductState) {
        cartRepository.deleteCartProduct(cartProductState.productId)
        loadCart()
    }

    override fun checkAll() {
        val cartProducts: List<CartProduct> = cartRepository.getAll()
        val checked: Boolean = cartProducts.find { !it.checked } != null

        cartProducts.forEach {
            cartRepository.updateCartProductChecked(it.productId, checked)
        }
        view.setTotalCost(PaymentCalculator.totalPaymentAmount(cartRepository.getAll()).toInt())
    }

    private fun getMaxPageNumber(cartsSize: Int): Int {
        if (cartsSize == 0) return 1
        return (cartsSize - 1) / maxProductsPerPage + 1
    }
}
