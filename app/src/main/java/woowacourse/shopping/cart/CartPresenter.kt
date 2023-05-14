package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toViewModel
import woowacourse.shopping.data.repository.CartRepository

class CartPresenter(
    private val view: CartContract.View,
    private val cartRepository: CartRepository,
    private var currentPage: Int = 0,
    private val countPerPage: Int
) : CartContract.Presenter {
    init {
        updateCartPage()
    }

    override fun removeCartProduct(cartProductModel: CartProductModel) {
        cartRepository.deleteCartProductByOrdinal(cartProductModel.ordinal)
        updateCartPage()
    }

    override fun goToPreviousPage() {
        currentPage--
        updateCartPage()
    }

    override fun goToNextPage() {
        currentPage++
        updateCartPage()
    }

    private fun updateCartPage() {
        val cart = cartRepository.selectPage(currentPage, countPerPage)
        view.updateCart(cartProductsModel = cart.products.map { it.toViewModel() })
        view.updateNavigator(
            currentPage = currentPage + 1, maxPage = calculateMaxPage() + 1
        )
    }

    private fun calculateMaxPage(): Int = (cartRepository.selectAllCount() - 1) / countPerPage
}
