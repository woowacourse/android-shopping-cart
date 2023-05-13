package woowacourse.shopping.view.cart

import woowacourse.shopping.model.CartPageStatus
import woowacourse.shopping.model.CartProductModel

interface CartContract {
    interface View {
        fun showProducts(items: List<CartViewItem>)
        fun notifyRemoveItem(position: Int)
        fun showOtherPage()
    }

    interface Presenter {
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchPrevPage()
    }
}
