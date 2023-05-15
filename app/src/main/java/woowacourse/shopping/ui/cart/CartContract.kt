package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.ui.cart.cartAdapter.CartItemType

interface CartContract {

    interface View {
        fun setCarts(products: List<CartItemType.Cart>, pageUIModel: PageUIModel)
        fun navigateToItemDetail(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpCarts()
        fun moveToPageNext()
        fun moveToPagePrev()
        fun removeItem(id: Int)
        fun navigateToItemDetail(productId: Int)
        fun getPageIndex(): Int
        fun updateItem(productId: Int, count: Int): Int
    }
}
