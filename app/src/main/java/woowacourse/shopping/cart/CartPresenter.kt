package woowacourse.shopping.cart

import android.view.View
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.CheckableCartProductModel
import woowacourse.shopping.common.model.PageNavigatorModel
import woowacourse.shopping.common.model.mapper.CheckableCartProductMapper.toDomainModel
import woowacourse.shopping.common.model.mapper.CheckableCartProductMapper.toViewModel
import woowacourse.shopping.common.model.mapper.ProductMapper.toDomainModel
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.CheckableCartProduct

class CartPresenter(
    private val view: CartContract.View,
    private var cart: Cart = Cart(emptyList()),
    private val cartRepository: CartRepository,
    private var currentPage: Int = 0,
    private val countPerPage: Int
) : CartContract.Presenter {
    init {
        updateCartPage()
    }

    override fun deleteCartProduct(cartProduct: CartProductModel) {
        cartRepository.deleteCartProduct(cartProduct.product.toDomainModel())
        updateCartPage()
    }

    override fun loadPreviousPage() {
        currentPage--
        updateCartPage()
    }

    override fun loadNextPage() {
        currentPage++
        updateCartPage()
    }

    override fun minusCartProduct(cartProduct: CartProductModel) {
        cartRepository.minusCartProduct(cartProduct.product.toDomainModel())
        updateCartPage()
    }

    override fun plusCartProduct(cartProduct: CartProductModel) {
        cartRepository.plusCartProduct(cartProduct.product.toDomainModel())
        updateCartPage()
    }

    override fun checkCartProduct(
        checkableCartProduct: CheckableCartProductModel,
        isChecked: Boolean
    ) {
        cart = cart.checkProduct(checkableCartProduct.toDomainModel(), isChecked)
        updateCartPage()
    }

    private fun updateCartPage() {
        updateCart()
        updateNavigator()
        updateTotalPrice()
        updateOrderText()
    }

    private fun updateCart() {
        cart = getPagedCart()
        view.updateCart(cart.products.map { it.toViewModel() })
    }

    private fun updateNavigator() {
        val maxPage = calculateMaxPage()
        view.updateNavigator(
            PageNavigatorModel(
                isPagingAvailable(maxPage),
                !isFirstPage(currentPage),
                !isLastPage(currentPage, maxPage),
                currentPage
            )
        )
    }

    private fun updateTotalPrice() {
        view.updateTotalPrice(cart.calculateCheckedProductsPrice())
    }

    private fun updateOrderText() {
        view.updateOrderText(cart.calculateCheckedProductsCount())
    }

    private fun getPagedCart(): Cart {
        return Cart(
            cartRepository.selectPage(
                currentPage, countPerPage
            ).products.map { CheckableCartProduct(findChecked(it), it) }
        )
    }

    private fun findChecked(carProduct: CartProduct): Boolean {
        return cart.products.find { it.product.product == carProduct.product }.let {
            it?.checked ?: DEFAULT_CHECK
        }
    }

    private fun isLastPage(currentPage: Int, maxPage: Int) = currentPage == maxPage

    private fun isFirstPage(currentPage: Int) = currentPage == 0

    private fun isPagingAvailable(maxPage: Int) = if (maxPage < 1) View.GONE else View.VISIBLE

    private fun calculateMaxPage(): Int = (cartRepository.selectAllCount() - 1) / countPerPage

    companion object {
        private const val DEFAULT_CHECK = true
    }
}
