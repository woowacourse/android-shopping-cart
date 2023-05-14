package woowacourse.shopping.feature.cart

import woowacourse.shopping.feature.list.item.ListItem
import woowacourse.shopping.feature.model.CartProductState

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
        fun deleteCartProduct(item: ListItem)
    }
}
