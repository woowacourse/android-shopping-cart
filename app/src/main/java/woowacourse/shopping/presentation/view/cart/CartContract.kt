package woowacourse.shopping.presentation.view.cart

import androidx.lifecycle.LiveData
import woowacourse.shopping.presentation.model.CartProductModel

interface CartContract {
    interface View {
        fun setCartItemsView(carts: List<CartProductModel>)
        fun setCurrentPage(currentPage: Int)
        fun setEnableLeftButton(isEnabled: Boolean)
        fun setEnableRightButton(isEnabled: Boolean)
        fun updateTotalPrice(totalPrice: Int)
    }

    interface Presenter {
        val totalPrice: LiveData<Int>
        fun loadCartItems()
        fun deleteCartItem(itemId: Long)
        fun decrementPage()
        fun incrementPage()
        fun changeAllCartSelectedStatus(cartsId: List<Long>, isSelected: Boolean)
        fun changeCartSelectedStatus(productId: Long, isSelected: Boolean)
        fun updateProductCount(productId: Long, productCount: Int)
    }
}
