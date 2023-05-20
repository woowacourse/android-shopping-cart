package woowacourse.shopping.cart.contract

import woowacourse.shopping.cart.CartItem
import woowacourse.shopping.model.CartNavigationUIModel
import woowacourse.shopping.model.CartProductUIModel

interface CartContract {

    interface View {
        fun setCarts(products: List<CartItem>, cartNavigationUIModel: CartNavigationUIModel)
        fun navigateToItemDetail(cartProduct: CartProductUIModel)
    }

    interface Presenter {
        fun setUpCarts()
        fun pageUp()
        fun pageDown()
        fun removeItem(id: Int)
        fun navigateToItemDetail(cartProduct: CartProductUIModel)
        fun getOffset(): Int
        fun increaseCount(cartProduct: CartProductUIModel)
        fun decreaseCount(cartProduct: CartProductUIModel)
    }
}
