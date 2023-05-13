package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel

interface ShoppingCartContract {
    interface View {
        var presenter: Presenter
        fun removeCartProduct(cartProducts: List<CartProductUIModel>, index: Int)
    }

    interface Presenter {
        val cartProducts: List<CartProductUIModel>
        fun setRecentProducts()
        fun removeCartProduct(productUIModel: ProductUIModel)
    }
}
