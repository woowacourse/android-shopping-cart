package woowacourse.shopping.feature.cart

import woowacourse.shopping.feature.list.item.CartProductItem

interface CartActivityContract {
    interface View {
        fun setUpRecyclerView(cartItems: List<CartProductItem>)
        fun updateAdapterData(cartItems: List<CartProductItem>)
        fun setBeforeButtonListener(maxPageNumber: Int)
        fun setAfterButtonListener(maxPageNumber: Int)
    }

    interface Presenter {
        fun setUpData()
        fun setUpButton()
        fun updateDataEachPage(page: Int)
        fun deleteData(item: CartProductItem)
    }
}
