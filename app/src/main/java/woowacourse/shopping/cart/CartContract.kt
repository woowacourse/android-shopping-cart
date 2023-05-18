package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.PageNavigatorModel

interface CartContract {
    interface Presenter {
        fun deleteCartProduct(cartProduct: CartProductModel)
        fun loadPreviousPage()
        fun loadNextPage()
        fun minusCartProduct(cartProduct: CartProductModel)
        fun plusCartProduct(cartProduct: CartProductModel)
    }

    interface View {
        fun updateCart(cartProducts: List<CartProductModel>)
        fun updateNavigator(pageNavigatorModel: PageNavigatorModel)
    }
}
