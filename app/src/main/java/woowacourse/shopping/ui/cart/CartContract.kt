package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.utils.NonNullLiveData

interface CartContract {

    interface View {
        fun setPage(page: List<CartProductUIModel>, pageUIModel: PageUIModel)
        fun navigateToItemDetail(product: ProductUIModel)
    }

    interface Presenter {
        val totalPrice: NonNullLiveData<Int>
        val checkedCount: NonNullLiveData<Int>
        val allCheck: NonNullLiveData<Boolean>
        fun setUpView()
        fun setUpProductsCheck(checked: Boolean)
        fun moveToPageNext()
        fun moveToPagePrev()
        fun updateItemCount(productId: Int, count: Int)
        fun updateItemCheck(productId: Int, checked: Boolean)
        fun removeItem(productId: Int)
        fun getPageIndex(): Int
        fun navigateToItemDetail(productId: Int)
    }
}
