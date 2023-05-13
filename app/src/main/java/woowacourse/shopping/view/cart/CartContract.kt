package woowacourse.shopping.view.cart

import woowacourse.shopping.model.CartPageStatus
import woowacourse.shopping.model.CartProductModel

interface CartContract {
    interface View {
        fun showProducts(
            cartProducts: List<CartProductModel>,
            cartPageStatus: CartPageStatus
        )

        fun notifyRemoveItem(position: Int)
        fun showOtherPage(size: Int)
    }

    interface Presenter {
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchPrevPage()
    }
}
