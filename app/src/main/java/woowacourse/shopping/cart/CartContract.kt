package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.PageNavigatorModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartProductModel: CartProductModel)

        fun loadPreviousPage()

        fun loadNextPage()
    }

    interface View {
        fun updateCart(cartProductsModel: List<CartProductModel>)
        fun updateNavigator(pageNavigatorModel: PageNavigatorModel)
    }
}
