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
        fun observeTotalPrice()
        fun handleBackButtonClicked()
    }

    interface Presenter {
        val totalPrice: LiveData<Int>
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchUndoPage()
        fun plusCount(id: Int)
        fun subCount(id: Int)
        fun handleNextStep(itemId: Int)
        fun setupTotalPrice()
        fun updateItemCheck(id: Int, checked: Boolean)
    }
}
