package woowacourse.shopping.ui.cart.contract

import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.CartItemType

interface CartContract {

    interface View {
        fun setCarts(products: List<CartItemType.Cart>, cartUIModel: CartUIModel)
        fun navigateToItemDetail(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpCarts()
        fun pageUp()
        fun pageDown()
        fun removeItem(id: Int)
        fun navigateToItemDetail(product: ProductUIModel)
        fun getOffset(): Int
    }
}
