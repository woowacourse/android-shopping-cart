package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

interface ShoppingCartContract {
    interface View {
        var presenter: Presenter
        fun updateCartProduct(cartProducts: List<CartProductUIModel>)
        fun activatePageUpCounter()
        fun deactivatePageUpCounter()
        fun activatePageDownCounter()
        fun deactivatePageDownCounter()
        fun updatePageCounter(count: Int)
    }

    interface Presenter {
        val cartProducts: List<CartProductUIModel>
        fun loadCartProducts(): List<CartProductUIModel>
        fun removeCartProduct(productUIModel: ProductUIModel)
        fun pageUpClick(isActivated: Boolean)
        fun pageDownClick(isActivated: Boolean)
    }
}
