package woowacourse.shopping.feature.cart

import woowacourse.shopping.model.CartProductState

interface CartContract {

    interface View {
        fun setCartProducts(cartProducts: List<CartProductState>)
        fun setCartPageNumber(number: Int)
        fun setCartPageNumberPlusEnable(isEnable: Boolean)
        fun setCartPageNumberMinusEnable(isEnable: Boolean)
    }

    interface Presenter {
        fun loadCart()
        fun plusPageNumber()
        fun minusPageNumber()
        fun plusCountNumber(cartProductState: CartProductState, count: Int)
        fun minusCountNumber(cartProductState: CartProductState, count: Int)
        fun deleteCartProduct(cartProductState: CartProductState)
    }
}
