package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartOrdinalProductModel
import woowacourse.shopping.common.model.PageNavigatorModel

interface CartContract {
    interface Presenter {
        fun removeCartProduct(cartOrdinalProductModel: CartOrdinalProductModel)

        fun loadPreviousPage()

        fun loadNextPage()
    }

    interface View {
        fun updateCart(cartProductsModel: List<CartOrdinalProductModel>)
        fun updateNavigator(pageNavigatorModel: PageNavigatorModel)
    }
}
