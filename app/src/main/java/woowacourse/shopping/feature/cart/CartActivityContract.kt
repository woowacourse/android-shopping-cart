package woowacourse.shopping.feature.cart

import woowacourse.shopping.feature.list.item.CartProductItem

interface CartActivityContract {
    interface View {
        val presenter: CartActivityPresenter

        fun setUpRecyclerView(cartItems: List<CartProductItem>)
        fun updateAdapterData(cartItems: List<CartProductItem>)
        fun setButtonListener(maxPage: Int)
        fun setPage(page: Int)
        fun updateButtonsEnabledState(page: Int, maxPage: Int)
    }

    interface Presenter {
        fun setUpData()
        fun updateData()
        fun deleteData(item: CartProductItem)
        fun setUpButton()
        fun nextPage()
        fun previousPage()
    }
}
