package woowacourse.shopping.view.cart

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
        fun handleBackButtonClicked()
    }

    interface Presenter {
        fun fetchProducts()
        fun removeProduct(id: Int)
        fun fetchNextPage()
        fun fetchUndoPage()
        fun handleNextStep(itemId: Int)
    }
}
