package woowacourse.shopping.feature.cart

import woowacourse.shopping.feature.list.item.ProductView.CartProductItem

interface CartActivityContract {
    interface View {
        val presenter: CartActivityPresenter

        fun setUpAdapterData(cartItems: List<CartProductItem>)
        fun updateAdapterData(cartItems: List<CartProductItem>, selectedStates: List<Boolean>)

        fun setButtonClickListener(maxPage: Int)
        fun updateButtonsEnabledState(page: Int, maxPage: Int)

        fun setPage(page: Int)
        fun setPrice(totalPrice: Int)
        fun setOrderNumber(number: Int)
        fun setAllSelected(isChecked: Boolean)
    }

    interface Presenter {
        fun loadInitialData()
        fun setUpButton()
        fun setBottomView()

        fun removeItem(item: CartProductItem)
        fun updateItem(item: CartProductItem, isPlus: Boolean)
        fun selectAllItems(isChecked: Boolean)

        fun toggleItemChecked(item: CartProductItem)
        fun onNextPage()
        fun onPreviousPage()
    }
}
