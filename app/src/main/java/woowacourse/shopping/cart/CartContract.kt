package woowacourse.shopping.cart

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.CheckableCartProductModel
import woowacourse.shopping.common.model.PageNavigatorModel

interface CartContract {
    interface Presenter {
        fun deleteCartProduct(cartProduct: CartProductModel)
        fun loadPreviousPage()
        fun loadNextPage()
        fun minusCartProduct(cartProduct: CartProductModel)
        fun plusCartProduct(cartProduct: CartProductModel)
        fun checkCartProduct(checkableCartProduct: CheckableCartProductModel, isChecked: Boolean)
    }

    interface View {
        fun updateCart(checkableCartProducts: List<CheckableCartProductModel>)
        fun updateNavigator(pageNavigatorModel: PageNavigatorModel)
    }
}
