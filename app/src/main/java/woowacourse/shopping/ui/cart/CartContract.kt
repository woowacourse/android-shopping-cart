package woowacourse.shopping.ui.cart

import androidx.lifecycle.LiveData
import woowacourse.shopping.model.CartProductUIModel
import woowacourse.shopping.model.PageUIModel
import woowacourse.shopping.model.ProductUIModel

interface CartContract {

    interface View {
        fun setPage(page: List<CartProductUIModel>, pageUIModel: PageUIModel)
        fun navigateToItemDetail(product: ProductUIModel)
    }

    interface Presenter {
        val totalPrice: LiveData<Int>
        val checkedCount: LiveData<Int>
        val allCheck: LiveData<Boolean>
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
