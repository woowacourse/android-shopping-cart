package woowacourse.shopping.feature.cart

import woowacourse.shopping.feature.list.item.CartProductListItem

interface CartActivityContract {
    interface View {
        fun setUpRecyclerView(cartItems: List<CartProductListItem>)
        fun updateAdapterData(cartItems: List<CartProductListItem>)
        fun setBeforeButtonListener(maxPageNumber: Int)
        fun setAfterButtonListener(maxPageNumber: Int)
    }

    interface Presenter {
        fun setUpData()
        fun setUpButton()
        fun updateDataEachPage(page: Int)
        fun deleteData(item: CartProductListItem)
    }
}
