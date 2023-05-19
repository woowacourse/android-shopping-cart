package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.utils.NonNullLiveData

interface CartContract {

    interface View {
        fun setCarts(products: List<CartProductUIModel>, pageUIModel: PageUIModel)
        fun navigateToItemDetail(product: ProductUIModel)
        fun setAllItemCheck(all: Boolean)
    }

    interface Presenter {
        val totalPrice: NonNullLiveData<Int>
        val checkedCount: NonNullLiveData<Int>
        fun setUpCarts()
        fun moveToPageNext()
        fun moveToPagePrev()
        fun removeProduct(productId: Int)
        fun navigateToItemDetail(productId: Int)
        fun getPageIndex(): Int
        fun updateItemCount(productId: Int, count: Int)
        fun updateItemCheck(productId: Int, checked: Boolean)
        fun setProductsCheck(checked: Boolean)
    }
}
