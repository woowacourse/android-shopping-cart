package woowacourse.shopping.cart.contract

import woowacourse.shopping.cart.CartItem
import woowacourse.shopping.model.CartUIModel
import woowacourse.shopping.model.ProductUIModel

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

        fun saveOffsetState(outState: MutableMap<String, Int>)

        fun restoreOffsetState(state: Map<String, Int>)
    }
}
