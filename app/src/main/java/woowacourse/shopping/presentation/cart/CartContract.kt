package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.model.CartProductInfoModel

interface CartContract {
    interface Presenter {
        fun updateCart()
        fun deleteProduct(cartProductInfoModel: CartProductInfoModel)
        fun plusPage()
        fun minusPage()
        fun updatePlusButtonState()
        fun updateMinusButtonState()
    }

    interface View {
        fun setCartItems(productModels: List<CartProductInfoModel>)
        fun setPage(count: Int)
        fun setUpPlusPageButtonState(isEnable: Boolean)
        fun setUpMinusPageButtonState(isEnable: Boolean)
    }
}
