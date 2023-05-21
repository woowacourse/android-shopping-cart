package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductState

interface CartContract {

    interface View {
        fun setCartProducts(cartProducts: List<CartProductState>)
        fun setCartPageNumber(number: Int)
        fun setCartPageNumberPlusEnable(isEnable: Boolean)
        fun setCartPageNumberMinusEnable(isEnable: Boolean)
        fun setCartProductCount(count: Int)
        fun setTotalCost(paymentAmount: Int)
        fun showPageSelectorView()
        fun hidePageSelectorView()
    }

    interface Presenter {
        fun loadCart()
        fun loadCheckedCartProductCount()
        fun plusPageNumber()
        fun minusPageNumber()
        fun updateCount(productId: Int, count: Int)
        fun updateChecked(productId: Int, checked: Boolean)
        fun deleteCartProduct(cartProductState: CartProductState)
        fun checkAll()
    }
}
