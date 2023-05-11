package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import woowacourse.shopping.domain.model.ProductInCart
import woowacourse.shopping.domain.repository.ShoppingCartRepository

class ShoppingCartPresenter(
    private val view: ShoppingCartContract.View,
    private val shoppingCartRepository: ShoppingCartRepository,
) : ShoppingCartContract.Presenter {
    lateinit var shoppingCart: List<ProductInCart>

    override fun getShoppingCart(page: Int) {
        val shoppingCart = shoppingCartRepository.getShoppingCart(SHOPPING_CART_ITEM_COUNT, page)

        view.setShoppingCart(shoppingCart)
    }

    override fun getShoppingCartSize() {
        shoppingCartRepository.getShoppingCartSize()
    }

    companion object {
        private const val SHOPPING_CART_ITEM_COUNT = 5
    }
}
