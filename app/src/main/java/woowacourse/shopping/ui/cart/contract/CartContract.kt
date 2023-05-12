package woowacourse.shopping.ui.cart.contract

import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.CartItem

interface CartContract {

    interface View {
        fun setCarts(products: List<CartItem>, cartUIModel: CartUIModel)
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
