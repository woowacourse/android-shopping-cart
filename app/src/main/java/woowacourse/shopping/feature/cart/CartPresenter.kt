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

        val items: List<CartProductState> =
            cartRepository.getAll().filterIndexed { index, _ ->
                index in startIndex..endIndex
            }.map(CartProduct::toUi)

        view.setCartPageNumber(pageNumber)
        view.setCartProducts(items)

        view.hidePageSelectorView()
        if (minPageNumber < maxPageNumber) view.showPageSelectorView()
    }

    override fun plusPageNumber() {
        val nextPage: Int = pageNumber + 1

        view.setCartPageNumberMinusEnable(true)
        if (nextPage > maxPageNumber) return
        if (nextPage < maxPageNumber) view.setCartPageNumberPlusEnable(true)
        if (nextPage == maxPageNumber) view.setCartPageNumberPlusEnable(false)

        pageNumber++
        loadCart()
    }

    override fun minusPageNumber() {
        val nextPage: Int = pageNumber - 1

        view.setCartPageNumberPlusEnable(true)
        if (nextPage < minPageNumber) return
        if (nextPage > minPageNumber) view.setCartPageNumberMinusEnable(true)
        if (nextPage == minPageNumber) view.setCartPageNumberMinusEnable(false)

        pageNumber--
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
