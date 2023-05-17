package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.ProductUIModel

interface CartContract {

    interface View {
        fun setCarts(products: List<CartProductUIModel>, pageUIModel: PageUIModel)
        fun navigateToItemDetail(product: ProductUIModel)
        fun updateBottom(totalPrice: Int, totalCount: Int)
        fun setAllItemCheck(all: Boolean)
    }

    interface Presenter {
        fun setUpCarts()
        fun moveToPageNext()
        fun moveToPagePrev()
        fun removeItem(id: Int)
        fun navigateToItemDetail(productId: Int)
        fun getPageIndex(): Int
        fun updateItem(productId: Int, count: Int): Int
        fun updateItemCheck(productId: Int, selected: Boolean)
        fun updatePriceAndCount()
        fun updateAllItemCheck(checked: Boolean)
    }
}
