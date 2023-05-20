package woowacourse.shopping.view.cart

import androidx.lifecycle.LiveData
import woowacourse.shopping.model.CartProductModel

interface CartContract {
    interface View {
        fun showProducts(
            cartProducts: List<CartProductModel>,
            isExistUndo: Boolean,
            isExistNext: Boolean,
            count: String,
        )

        fun notifyRemoveItem(position: Int)
        fun showOtherPage(size: Int)
        fun onAllCheckSelected()
        fun handleBackButtonClicked()
        fun showAllCheckBox()
    }

    interface Presenter {
        val totalPrice: LiveData<Int>
        val totalCount: LiveData<Int>
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchUndoPage()
        fun plusCount(id: Int)
        fun subCount(id: Int)
        fun setupTotalPrice()
        fun setupTotalCount()
        fun updateItemCheck(id: Int, checked: Boolean)
        fun setAllCheck()
        fun handleNextStep(itemId: Int)
        fun setAllCheckCondition(): Boolean
        fun setAllUncheck()
    }
}
