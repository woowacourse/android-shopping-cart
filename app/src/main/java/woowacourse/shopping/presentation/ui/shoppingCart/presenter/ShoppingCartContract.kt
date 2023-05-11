package woowacourse.shopping.presentation.ui.shoppingCart.presenter

import woowacourse.shopping.domain.model.ProductInCart

interface ShoppingCartContract {
    interface View {
        val presenter: Presenter

        fun setShoppingCart(shoppingCart: List<ProductInCart>)
    }

    interface Presenter {
        fun getShoppingCart(page: Int)
        fun getShoppingCartSize()
    }
}
