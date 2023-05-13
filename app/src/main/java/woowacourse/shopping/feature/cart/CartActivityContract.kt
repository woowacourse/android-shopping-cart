package woowacourse.shopping.feature.cart

import woowacourse.shopping.feature.list.item.CartProductItem

interface CartActivityContract {
    interface View {
        fun setUpRecyclerView(cartItems: List<CartProductItem>)
        fun updateAdapterData(cartItems: List<CartProductItem>)
        fun setButtonListener(maxPage: Int)
    }

    interface Presenter {
        fun setUpData(page: Int)
        fun deleteData(page: Int, item: CartProductItem)
        fun setUpButton()
    }
}
